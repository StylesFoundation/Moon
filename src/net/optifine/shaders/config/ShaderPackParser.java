package net.optifine.shaders.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.optifine.expr.ExpressionFloatArrayCached;
import net.optifine.expr.ExpressionFloatCached;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.expr.ParseException;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Program;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.uniform.CustomUniform;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderExpressionResolver;
import net.optifine.shaders.uniform.UniformType;
import net.optifine.util.StrUtils;

public class ShaderPackParser {
   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
   private static final Pattern b = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/.]+)\".*$");
   private static final Set<String> setConstNames = makeSetConstNames();
   private static final Map<String, Integer> mapAlphaFuncs = Method730();
   private static final Map<String, Integer> mapBlendFactors = Method731();

   public static ShaderOption[] parseShaderPackOptions(IShaderPack iShaderPack, String[] arr, List<Integer> list) {
      if (iShaderPack == null) {
         return new ShaderOption[0];
      }

      Map<String, ShaderOption> var6 = new HashMap<>();
      collectShaderOptions(iShaderPack, "/shaders", arr, var6);
      Iterator var7 = list.iterator();

      while (var7.hasNext()) {
         String var9 = "/shaders/world" + (Integer)var7.next();
         collectShaderOptions(iShaderPack, var9, arr, var6);
      }

      ShaderOption[] var8 = var6.values().toArray(new ShaderOption[0]);
      Comparator<ShaderOption> var10 = (shaderOption1, shaderOption2) -> shaderOption1.getName().compareToIgnoreCase(shaderOption2.getName());
      Arrays.sort(var8, var10);
      return var8;
   }

   private static void collectShaderOptions(IShaderPack iShaderPack, String string, String[] arr, Map<String, ShaderOption> map) {
      for (String var10 : arr) {
         if (!var10.equals("")) {
            String var11 = string + "/" + var10 + ".vsh";
            String var12 = string + "/" + var10 + ".fsh";
            collectShaderOptions(iShaderPack, var11, map);
            collectShaderOptions(iShaderPack, var12, map);
         }
      }
   }

   private static void collectShaderOptions(IShaderPack iShaderPack, String string, Map<String, ShaderOption> map) {
      String[] var6 = getLines(iShaderPack, string);
      String[] var7 = var6;
      int var8 = var7.length;

      for (int var9 = 0; var9 < var8; var9++) {
         ShaderOption var11 = getShaderOption(var7[var9], string);
         if (var11 != null && !var11.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!var11.checkUsed() || isOptionUsed(var11, var6))) {
            String var12 = var11.getName();
            ShaderOption var13 = (ShaderOption)map.get(var12);
            if (var13 != null) {
               if (!Config.equals(var13.getValueDefault(), var11.getValueDefault())) {
                  Config.Method252("Ambiguous shader option: " + var11.getName());
                  Config.Method252(" - in " + Config.arrayToString(var13.getPaths()) + ": " + var13.getValueDefault());
                  Config.Method252(" - in " + Config.arrayToString(var11.getPaths()) + ": " + var11.getValueDefault());
                  var13.setEnabled(false);
               }

               if (var13.getDescription() == null || var13.getDescription().length() == 0) {
                  var13.setDescription(var11.getDescription());
               }

               var13.addPaths(var11.getPaths());
            } else {
               map.put(var12, var11);
            }
         }
      }
   }

   private static boolean isOptionUsed(ShaderOption shaderOption, String[] arr) {
      for (String var8 : arr) {
         if (shaderOption.isUsedInLine(var8)) {
            return true;
         }
      }

      return false;
   }

   private static String[] getLines(IShaderPack iShaderPack, String string) {
      try {
         ArrayList var4 = new ArrayList();
         String var5 = Method729(string, iShaderPack, 0, var4, 0);
         return var5 == null ? new String[0] : Config.Method273(new ByteArrayInputStream(var5.getBytes()));
      } catch (IOException var6) {
         Config.Method251(var6.getClass().getName() + ": " + var6.getMessage());
         return new String[0];
      }
   }

   private static ShaderOption getShaderOption(String string1, String string2) {
      ShaderOption var5 = null;
      if (null == null) {
         var5 = ShaderOptionSwitch.parseOption(string1, string2);
      }

      if (var5 == null) {
         var5 = ShaderOptionVariable.parseOption(string1, string2);
      }

      if (var5 != null) {
         return var5;
      }

      if (var5 == null) {
         var5 = ShaderOptionSwitchConst.parseOption(string1, string2);
      }

      if (var5 == null) {
         var5 = ShaderOptionVariableConst.parseOption(string1, string2);
      }

      return var5 != null && setConstNames.contains(var5.getName()) ? var5 : null;
   }

   private static Set<String> makeSetConstNames() {
      HashSet var2 = new HashSet();
      var2.add("shadowMapResolution");
      var2.add("shadowMapFov");
      var2.add("shadowDistance");
      var2.add("shadowDistanceRenderMul");
      var2.add("shadowIntervalSize");
      var2.add("generateShadowMipmap");
      var2.add("generateShadowColorMipmap");
      var2.add("shadowHardwareFiltering");
      var2.add("shadowHardwareFiltering0");
      var2.add("shadowHardwareFiltering1");
      var2.add("shadowtex0Mipmap");
      var2.add("shadowtexMipmap");
      var2.add("shadowtex1Mipmap");
      var2.add("shadowcolor0Mipmap");
      var2.add("shadowColor0Mipmap");
      var2.add("shadowcolor1Mipmap");
      var2.add("shadowColor1Mipmap");
      var2.add("shadowtex0Nearest");
      var2.add("shadowtexNearest");
      var2.add("shadow0MinMagNearest");
      var2.add("shadowtex1Nearest");
      var2.add("shadow1MinMagNearest");
      var2.add("shadowcolor0Nearest");
      var2.add("shadowColor0Nearest");
      var2.add("shadowColor0MinMagNearest");
      var2.add("shadowcolor1Nearest");
      var2.add("shadowColor1Nearest");
      var2.add("shadowColor1MinMagNearest");
      var2.add("wetnessHalflife");
      var2.add("drynessHalflife");
      var2.add("eyeBrightnessHalflife");
      var2.add("centerDepthHalflife");
      var2.add("sunPathRotation");
      var2.add("ambientOcclusionLevel");
      var2.add("superSamplingLevel");
      var2.add("noiseTextureResolution");
      return var2;
   }

   public static ShaderProfile[] parseProfiles(Properties properties, ShaderOption[] arr) {
      String var5 = "profile.";
      ArrayList<ShaderProfile> var6 = new ArrayList<>();

      for (Object var8Obj : properties.keySet()) {
         String var8 = (String)var8Obj;
         if (var8.startsWith(var5)) {
            String var9 = var8.substring(8);
            properties.getProperty(var8);
            HashSet var10 = new HashSet();
            ShaderProfile var11 = parseProfile(var9, properties, var10, arr);
            if (var11 != null) {
               var6.add(var11);
            }
         }
      }

      return var6.size() == 0 ? null : var6.toArray(new ShaderProfile[0]);
   }

   public static Map<String, IExpressionBool> parseProgramConditions(Properties properties, ShaderOption[] arr) {
      Pattern var6 = Pattern.compile("program\\.([^.]+)\\.enabled");
      HashMap<String, IExpressionBool> var7 = new HashMap<>();

      for (Object var9Obj : properties.keySet()) {
         String var9 = (String)var9Obj;
         Matcher var10 = var6.matcher(var9);
         if (var10.matches()) {
            String var11 = var10.group(1);
            IExpressionBool var13 = parseOptionExpression(properties.getProperty(var9).trim(), arr);
            if (var13 == null) {
               SMCLog.severe("Error parsing program condition: " + var9);
            } else {
               var7.put(var11, var13);
            }
         }
      }

      return var7;
   }

   private static IExpressionBool parseOptionExpression(String string, ShaderOption[] arr) {
      try {
         ShaderOptionResolver var4 = new ShaderOptionResolver(arr);
         return new ExpressionParser(var4).parseBool(string);
      } catch (ParseException var5) {
         SMCLog.warning(var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   public static Set<String> parseOptionSliders(Properties properties, ShaderOption[] arr) {
      HashSet var5 = new HashSet();
      String var6 = properties.getProperty("sliders");
      if (var6 != null) {
         for (String var11 : Config.tokenize(var6, " ")) {
            ShaderOption var12 = ShaderUtils.getShaderOption(var11, arr);
            if (var12 == null) {
               Config.Method252("Invalid shader option: " + var11);
            } else {
               var5.add(var11);
            }
         }
      }

      return var5;
   }

   private static ShaderProfile parseProfile(String string, Properties properties, Set<String> set, ShaderOption[] arr) {
      String var7 = "profile.";
      String var8 = var7 + string;
      if (set.contains(var8)) {
         Config.Method252("[Shaders] Profile already parsed: " + string);
         return null;
      }

      set.add(string);
      ShaderProfile var9 = new ShaderProfile(string);

      for (String var15 : Config.tokenize(properties.getProperty(var8), " ")) {
         if (var15.startsWith(var7)) {
            ShaderProfile var17 = parseProfile(var15.substring(8), properties, set, arr);
            if (var9 != null) {
               var9.addOptionValues(var17);
               var9.addDisabledPrograms(var17.getDisabledPrograms());
            }
         } else {
            String[] var16 = Config.tokenize(var15, ":=");
            if (var16.length == 1) {
               String var21 = var16[0];
               boolean var18 = true;
               if (var21.startsWith("!")) {
                  var18 = false;
                  var21 = var21.substring(1);
               }

               String var19 = "program.";
               if (var21.startsWith(var19)) {
                  String var20 = var21.substring(8);
                  if (!Shaders.isProgramPath(var20)) {
                     Config.Method252("Invalid program: " + var20 + " in profile: " + var9.getName());
                  } else if (var18) {
                     var9.removeDisabledProgram(var20);
                  } else {
                     var9.addDisabledProgram(var20);
                  }
               } else {
                  ShaderOption var25 = ShaderUtils.getShaderOption(var21, arr);
                  if (!(var25 instanceof ShaderOptionSwitch)) {
                     Config.Method252("[Shaders] Invalid option: " + var21);
                  } else {
                     var9.addOptionValue(var21, String.valueOf(var18));
                     var25.setVisible(true);
                  }
               }
            } else if (var16.length != 2) {
               Config.Method252("[Shaders] Invalid option value: " + var15);
            } else {
               String var22 = var16[0];
               String var23 = var16[1];
               ShaderOption var24 = ShaderUtils.getShaderOption(var22, arr);
               if (var24 == null) {
                  Config.Method252("[Shaders] Invalid option: " + var15);
               } else if (!var24.isValidValue(var23)) {
                  Config.Method252("[Shaders] Invalid value: " + var15);
               } else {
                  var24.setVisible(true);
                  var9.addOptionValue(var22, var23);
               }
            }
         }
      }

      return var9;
   }

   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties properties, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
      HashMap var5 = new HashMap();
      parseGuiScreen("screen", properties, var5, shaderProfiles, shaderOptions);
      return var5.isEmpty() ? null : var5;
   }

   private static boolean parseGuiScreen(String string, Properties properties, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
      String var8 = properties.getProperty(string);
      if (var8 == null) {
         return false;
      }

      ArrayList<ShaderOption> var9 = new ArrayList<>();
      HashSet var10 = new HashSet();

      for (String var15 : Config.tokenize(var8, " ")) {
         if (var15.equals("<empty>")) {
            var9.add(null);
         } else if (var10.contains(var15)) {
            Config.Method252("[Shaders] Duplicate option: " + var15 + ", key: " + string);
         } else {
            var10.add(var15);
            if (var15.equals("<profile>")) {
               if (shaderProfiles == null) {
                  Config.Method252("[Shaders] Option profile can not be used, no profiles defined: " + var15 + ", key: " + string);
               } else {
                  ShaderOptionProfile var16 = new ShaderOptionProfile(shaderProfiles, shaderOptions);
                  var9.add(var16);
               }
            } else if (var15.equals("*")) {
               ShaderOptionRest var21 = new ShaderOptionRest("<rest>");
               var9.add(var21);
            } else if (var15.startsWith("[") && var15.endsWith("]")) {
               String var23 = StrUtils.removePrefixSuffix(var15, "[", "]");
               if (!var23.matches("^[a-zA-Z0-9_]+$")) {
                  Config.Method252("[Shaders] Invalid screen: " + var15 + ", key: " + string);
               } else if (!parseGuiScreen("screen." + var23, properties, map, shaderProfiles, shaderOptions)) {
                  Config.Method252("[Shaders] Invalid screen: " + var15 + ", key: " + string);
               } else {
                  ShaderOptionScreen var17 = new ShaderOptionScreen(var23);
                  var9.add(var17);
               }
            } else {
               ShaderOption var22 = ShaderUtils.getShaderOption(var15, shaderOptions);
               if (var22 == null) {
                  Config.Method252("[Shaders] Invalid option: " + var15 + ", key: " + string);
                  var9.add(null);
               } else {
                  var22.setVisible(true);
                  var9.add(var22);
               }
            }
         }
      }

      ShaderOption[] var18 = var9.toArray(new ShaderOption[0]);
      int var19 = Config.parseInt(properties.getProperty(string + ".columns"), 2);
      ScreenShaderOptions var20 = new ScreenShaderOptions(string, var18, var19);
      map.put(string, var20);
      return true;
   }

   public static BufferedReader resolveIncludes(
      BufferedReader bufferedReader, String string, IShaderPack iShaderPack, int integer4, List<String> list, int integer6
   ) throws IOException {
      String var9 = "/";
      int var10 = string.lastIndexOf("/");
      if (var10 >= 0) {
         var9 = string.substring(0, var10);
      }

      CharArrayWriter var11 = new CharArrayWriter();
      int var12 = -1;
      LinkedHashSet<ShaderMacro> var13 = new LinkedHashSet<>();
      int var14 = 1;

      while (true) {
         String var15 = bufferedReader.readLine();
         if (var15 == null) {
            char[] var24 = var11.toCharArray();
            if (var12 >= 0 && var13.size() > 0) {
               StringBuilder var27 = new StringBuilder();

               for (ShaderMacro var33 : var13) {
                  var27.append("#define ");
                  var27.append(var33.getName());
                  var27.append(" ");
                  var27.append(var33.getValue());
                  var27.append("\n");
               }

               String var31 = var27.toString();
               StringBuilder var34 = new StringBuilder(new String(var24));
               var34.insert(var12, var31);
               var24 = var34.toString().toCharArray();
            }

            CharArrayReader var28 = new CharArrayReader(var24);
            return new BufferedReader(var28);
         }

         if (var12 < 0) {
            Matcher var16 = PATTERN_VERSION.matcher(var15);
            if (var16.matches()) {
               String var17 = ShaderMacros.getFixedMacroLines() + ShaderMacros.getOptionMacroLines();
               String var18 = var15 + "\n" + var17;
               String var19 = "#line " + (var14 + 1) + " " + integer4;
               var15 = var18 + var19;
               var12 = var11.size() + var18.length();
            }
         }

         Matcher var23 = b.matcher(var15);
         if (var23.matches()) {
            String var25 = var23.group(1);
            boolean var29 = var25.startsWith("/");
            String var32 = var29 ? "/shaders" + var25 : var9 + "/" + var25;
            if (!list.contains(var32)) {
               list.add(var32);
            }

            int var20 = list.indexOf(var32) + 1;
            var15 = Method729(var32, iShaderPack, var20, list, integer6);
            if (var15 == null) {
               throw new IOException("Included file not found: " + string);
            }

            if (var15.endsWith("\n")) {
               var15 = var15.substring(0, var15.length() - 1);
            }

            String var21 = "#line 1 " + var20 + "\n";
            if (var15.startsWith("#version ")) {
               var21 = "";
            }

            var15 = var21 + var15 + "\n#line " + (var14 + 1) + " " + integer4;
         }

         if (var12 >= 0 && var15.contains(ShaderMacros.getPrefixMacro())) {
            ShaderMacro[] var26 = findMacros(var15, ShaderMacros.getExtensions());
            var13.addAll(Arrays.asList(var26));
         }

         var11.write(var15);
         var11.write("\n");
         var14++;
      }
   }

   private static ShaderMacro[] findMacros(String string, ShaderMacro[] arr) {
      ArrayList<ShaderMacro> var5 = new ArrayList<>();

      for (ShaderMacro var9 : arr) {
         if (string.contains(var9.getName())) {
            var5.add(var9);
         }
      }

      return var5.toArray(new ShaderMacro[0]);
   }

   private static String Method729(String string, IShaderPack iShaderPack, int integer3, List<String> list, int integer5) throws IOException {
      if (integer5 >= 10) {
         throw new IOException("#include depth exceeded: " + integer5 + ", file: " + string);
      }

      integer5++;
      InputStream var8 = iShaderPack.getResourceAsStream(string);
      if (var8 == null) {
         return null;
      }

      InputStreamReader var9 = new InputStreamReader(var8, StandardCharsets.US_ASCII);
      BufferedReader var10 = resolveIncludes(new BufferedReader(var9), string, iShaderPack, integer3, list, integer5);
      CharArrayWriter var11 = new CharArrayWriter();

      while (true) {
         String var12 = var10.readLine();
         if (var12 == null) {
            return var11.toString();
         }

         var11.write(var12);
         var11.write("\n");
      }
   }

   public static CustomUniforms parseCustomUniforms(Properties props)
   {
      String s = "uniform";
      String s1 = "variable";
      String s2 = s + ".";
      String s3 = s1 + ".";
      Map<String, IExpression> map = new HashMap<>();
      List<CustomUniform> list = new ArrayList<>();

      for (Object o : props.keySet())
      {
         String s4 = String.valueOf(o);
         String[] astring = Config.tokenize(s4, ".");

         if (astring.length == 3)
         {
            String s5 = astring[0];
            String s6 = astring[1];
            String s7 = astring[2];
            String s8 = props.getProperty(s4).trim();

            if (map.containsKey(s7))
            {
               SMCLog.warning("Expression already defined: " + s7);
            }
            else if (s5.equals(s) || s5.equals(s1))
            {
               SMCLog.info("Custom " + s5 + ": " + s7);
               CustomUniform customuniform = parseCustomUniform(s5, s7, s6, s8, map);

               if (customuniform != null)
               {
                  map.put(s7, customuniform.getExpression());

                  if (!s5.equals(s1))
                  {
                     list.add(customuniform);
                  }
               }
            }
         }
      }

      if (list.size() <= 0)
      {
         return null;
      }
      else
      {
         CustomUniform[] acustomuniform = (CustomUniform[])((CustomUniform[])list.toArray(new CustomUniform[list.size()]));
         CustomUniforms customuniforms = new CustomUniforms(acustomuniform, map);
         return customuniforms;
      }
   }

   private static CustomUniform parseCustomUniform(String string1, String string2, String string3, String string4, Map<String, IExpression> map) {
      try {
         UniformType var8 = UniformType.parse(string3);
         if (var8 == null) {
            SMCLog.warning("Unknown " + string1 + " type: " + var8);
            return null;
         } else {
            ShaderExpressionResolver var9 = new ShaderExpressionResolver(map);
            IExpression var11 = new ExpressionParser(var9).parse(string4);
            ExpressionType var12 = var11.getExpressionType();
            if (!var8.matchesExpressionType(var12)) {
               String var13 = string2;
               UniformType var14 = var8;
               String var15 = string1;
               ExpressionType var16 = var12;
               String var17 = string1;
               SMCLog.warning("Expression type does not match " + var17 + " type, expression: " + var16 + ", " + var15 + ": " + var14 + " " + var13);
               return null;
            } else {
               var11 = makeExpressionCached(var11);
               return new CustomUniform(string2, var8, var11);
            }
         }
      } catch (ParseException var18) {
         SMCLog.warning(var18.getClass().getName() + ": " + var18.getMessage());
         return null;
      }
   }

   private static IExpression makeExpressionCached(IExpression iExpression) {
      return iExpression instanceof IExpressionFloat
         ? new ExpressionFloatCached((IExpressionFloat)iExpression)
         : (iExpression instanceof IExpressionFloatArray ? new ExpressionFloatArrayCached((IExpressionFloatArray)iExpression) : iExpression);
   }

   public static void parseAlphaStates(Properties properties) {
      for (Object var5Obj : properties.keySet()) {
         String var5 = (String)var5Obj;
         String[] var6 = Config.tokenize(var5, ".");
         if (var6.length == 2) {
            String var7 = var6[0];
            String var8 = var6[1];
            if (var7.equals("alphaTest")) {
               Program var9 = Shaders.getProgram(var8);
               if (var9 == null) {
                  SMCLog.severe("Invalid program name: " + var8);
               } else {
                  GlAlphaState var11 = parseAlphaState(properties.getProperty(var5).trim());
                  if (var11 != null) {
                     var9.setAlphaState(var11);
                  }
               }
            }
         }
      }
   }

   private static GlAlphaState parseAlphaState(String string) {
      String[] var4 = Config.tokenize(string, " ");
      if (var4.length == 1) {
         String var5 = var4[0];
         if (var5.equals("off") || var5.equals("false")) {
            return new GlAlphaState(false);
         }
      } else if (var4.length == 2) {
         String var9 = var4[0];
         String var6 = var4[1];
         Integer var7 = mapAlphaFuncs.get(var9);
         float var8 = Config.parseFloat(var6, -1.0F);
         if (var7 != null && var8 >= 0.0F) {
            return new GlAlphaState(true, var7, var8);
         }
      }

      SMCLog.severe("Invalid alpha test: " + string);
      return null;
   }

   public static void parseBlendStates(Properties properties) {
      for (Object var5Obj : properties.keySet()) {
         String var5 = (String)var5Obj;
         String[] var6 = Config.tokenize(var5, ".");
         if (var6.length == 2) {
            String var7 = var6[0];
            String var8 = var6[1];
            if (var7.equals("blend")) {
               Program var9 = Shaders.getProgram(var8);
               if (var9 == null) {
                  SMCLog.severe("Invalid program name: " + var8);
               } else {
                  GlBlendState var11 = parseBlendState(properties.getProperty(var5).trim());
                  if (var11 != null) {
                     var9.setBlendState(var11);
                  }
               }
            }
         }
      }
   }

   private static GlBlendState parseBlendState(String string) {
      String[] var4 = Config.tokenize(string, " ");
      if (var4.length == 1) {
         String var5 = var4[0];
         if (var5.equals("off") || var5.equals("false")) {
            return new GlBlendState(false);
         }
      } else if (var4.length == 2 || var4.length == 4) {
         String var13 = var4[0];
         String var6 = var4[1];
         String var7 = var13;
         String var8 = var6;
         if (var4.length == 4) {
            var7 = var4[2];
            var8 = var4[3];
         }

         Integer var9 = mapBlendFactors.get(var13);
         Integer var10 = mapBlendFactors.get(var6);
         Integer var11 = mapBlendFactors.get(var7);
         Integer var12 = mapBlendFactors.get(var8);
         if (var9 != null && var10 != null && var11 != null && var12 != null) {
            return new GlBlendState(true, var9, var10, var11, var12);
         }
      }

      SMCLog.severe("Invalid blend mode: " + string);
      return null;
   }

   public static void parseRenderScales(Properties properties) {
      for (Object var5Obj : properties.keySet()) {
         String var5 = (String)var5Obj;
         String[] var6 = Config.tokenize(var5, ".");
         if (var6.length == 2) {
            String var7 = var6[0];
            String var8 = var6[1];
            if (var7.equals("scale")) {
               Program var9 = Shaders.getProgram(var8);
               if (var9 == null) {
                  SMCLog.severe("Invalid program name: " + var8);
               } else {
                  RenderScale var11 = parseRenderScale(properties.getProperty(var5).trim());
                  if (var11 != null) {
                     var9.setRenderScale(var11);
                  }
               }
            }
         }
      }
   }

   private static RenderScale parseRenderScale(String string) {
      String[] var4 = Config.tokenize(string, " ");
      float var5 = Config.parseFloat(var4[0], -1.0F);
      float var6 = 0.0F;
      float var7 = 0.0F;
      if (var4.length > 1) {
         if (var4.length != 3) {
            SMCLog.severe("Invalid render scale: " + string);
            return null;
         }

         var6 = Config.parseFloat(var4[1], -1.0F);
         var7 = Config.parseFloat(var4[2], -1.0F);
      }

      if (Config.between(var5, 0.0F, 1.0F) && Config.between(var6, 0.0F, 1.0F) && Config.between(var7, 0.0F, 1.0F)) {
         return new RenderScale(var5, var6, var7);
      }

      SMCLog.severe("Invalid render scale: " + string);
      return null;
   }

   public static void parseBuffersFlip(Properties properties) {
      for (Object var5Obj : properties.keySet()) {
         String var5 = (String)var5Obj;
         String[] var6 = Config.tokenize(var5, ".");
         if (var6.length == 3) {
            String var7 = var6[0];
            String var8 = var6[1];
            String var9 = var6[2];
            if (var7.equals("flip")) {
               Program var10 = Shaders.getProgram(var8);
               if (var10 == null) {
                  SMCLog.severe("Invalid program name: " + var8);
               } else {
                  Boolean[] var11 = var10.getBuffersFlip();
                  int var12 = Shaders.getBufferIndexFromString(var9);
                  if (var12 >= 0 && var12 < var11.length) {
                     String var13 = properties.getProperty(var5).trim();
                     Boolean var14 = Config.parseBoolean(var13, null);
                     if (var14 == null) {
                        SMCLog.severe("Invalid boolean value: " + var13);
                     } else {
                        var11[var12] = var14;
                     }
                  } else {
                     SMCLog.severe("Invalid buffer name: " + var9);
                  }
               }
            }
         }
      }
   }

   private static Map<String, Integer> Method730() {
      return Map.of("NEVER", 512, "LESS", 513, "EQUAL", 514, "LEQUAL", 515, "GREATER", 516, "NOTEQUAL", 517, "GEQUAL", 518, "ALWAYS", 519);
   }

   private static Map<String, Integer> Method731() {
      return Map.ofEntries(
         Map.entry("ZERO", 0),
         Map.entry("ONE", 1),
         Map.entry("SRC_COLOR", 768),
         Map.entry("ONE_MINUS_SRC_COLOR", 769),
         Map.entry("DST_COLOR", 774),
         Map.entry("ONE_MINUS_DST_COLOR", 775),
         Map.entry("SRC_ALPHA", 770),
         Map.entry("ONE_MINUS_SRC_ALPHA", 771),
         Map.entry("DST_ALPHA", 772),
         Map.entry("ONE_MINUS_DST_ALPHA", 773),
         Map.entry("CONSTANT_COLOR", 32769),
         Map.entry("ONE_MINUS_CONSTANT_COLOR", 32770),
         Map.entry("CONSTANT_ALPHA", 32771),
         Map.entry("ONE_MINUS_CONSTANT_ALPHA", 32772),
         Map.entry("SRC_ALPHA_SATURATE", 776)
      );
   }
}
