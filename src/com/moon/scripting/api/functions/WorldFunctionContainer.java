package com.moon.scripting.api.functions;

import com.moon.scripting.api.functions.handlers.world.GetActiveEffectsHandler;
import com.moon.scripting.api.functions.handlers.world.GetBiomeHandler;
import com.moon.scripting.api.functions.handlers.world.GetBlockAtHandler;
import com.moon.scripting.api.functions.handlers.world.GetBlockAtVecHandler;
import com.moon.scripting.api.functions.handlers.world.GetDisplayNameHandler;
import com.moon.scripting.api.functions.handlers.world.GetEffectHandler;
import com.moon.scripting.api.functions.handlers.world.GetEntitiesHandler;
import com.moon.scripting.api.functions.handlers.world.GetFacingHandler;
import com.moon.scripting.api.functions.handlers.world.GetHealthHandler;
import com.moon.scripting.api.functions.handlers.world.GetHeightHandler;
import com.moon.scripting.api.functions.handlers.world.GetHurtResistantTimeHandler;
import com.moon.scripting.api.functions.handlers.world.GetHurtTimeHandler;
import com.moon.scripting.api.functions.handlers.world.GetMaxHealthHandler;
import com.moon.scripting.api.functions.handlers.world.GetNameHandler;
import com.moon.scripting.api.functions.handlers.world.GetPitchHandler;
import com.moon.scripting.api.functions.handlers.world.GetPlayersHandler;
import com.moon.scripting.api.functions.handlers.world.GetPositionHandler;
import com.moon.scripting.api.functions.handlers.world.GetPositionXHandler;
import com.moon.scripting.api.functions.handlers.world.GetPositionYHandler;
import com.moon.scripting.api.functions.handlers.world.GetPositionZHandler;
import com.moon.scripting.api.functions.handlers.world.GetPrevPositionHandler;
import com.moon.scripting.api.functions.handlers.world.GetPrevPositionXHandler;
import com.moon.scripting.api.functions.handlers.world.GetPrevPositionYHandler;
import com.moon.scripting.api.functions.handlers.world.GetPrevPositionZHandler;
import com.moon.scripting.api.functions.handlers.world.GetTicksExistedHandler;
import com.moon.scripting.api.functions.handlers.world.GetWidthHandler;
import com.moon.scripting.api.functions.handlers.world.GetYawHandler;
import com.moon.scripting.api.functions.handlers.world.HasEffectHandler;
import com.moon.scripting.api.functions.handlers.world.HeldItemHandler;
import com.moon.scripting.api.functions.handlers.world.IsBotHandler;
import com.moon.scripting.api.functions.handlers.world.IsBurningHandler;
import com.moon.scripting.api.functions.handlers.world.IsFriendHandler;
import com.moon.scripting.api.functions.handlers.world.IsInvisibleHandler;
import com.moon.scripting.api.functions.handlers.world.IsPlayerHandler;
import com.moon.scripting.api.functions.handlers.world.IsRidingHandler;
import com.moon.scripting.api.functions.handlers.world.IsSneakingHandler;
import com.moon.scripting.api.functions.handlers.world.IsSprintingHandler;
import com.moon.scripting.api.functions.handlers.world.IsTargetHandler;
import com.moon.scripting.api.functions.handlers.world.RemoveHandler;
import com.moon.scripting.api.functions.impl.WorldFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class WorldFunctionContainer extends FunctionContainer {
   public final WorldFunctionsImpl implementation = new WorldFunctionsImpl();

   public WorldFunctionContainer() {
      super("world");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("remove", new RemoveHandler(this));
      table.set("get_block_at", new GetBlockAtHandler(this));
      table.set("get_block_at_vec", new GetBlockAtVecHandler(this));
      table.set("get_entities", new GetEntitiesHandler(this));
      table.set("get_players", new GetPlayersHandler(this));
      table.set("get_name", new GetNameHandler(this));
      table.set("get_display_name", new GetDisplayNameHandler(this));
      table.set("get_hurt_time", new GetHurtTimeHandler(this));
      table.set("get_hurt_resistant_time", new GetHurtResistantTimeHandler(this));
      table.set("get_health", new GetHealthHandler(this));
      table.set("get_max_health", new GetMaxHealthHandler(this));
      table.set("is_sprinting", new IsSprintingHandler(this));
      table.set("is_sneaking", new IsSneakingHandler(this));
      table.set("is_invisible", new IsInvisibleHandler(this));
      table.set("is_burning", new IsBurningHandler(this));
      table.set("is_riding", new IsRidingHandler(this));
      table.set("get_facing", new GetFacingHandler(this));
      table.set("get_yaw", new GetYawHandler(this));
      table.set("get_pitch", new GetPitchHandler(this));
      table.set("get_ticks_existed", new GetTicksExistedHandler(this));
      table.set("is_player", new IsPlayerHandler(this));
      table.set("is_bot", new IsBotHandler(this));
      table.set("is_target", new IsTargetHandler(this));
      table.set("is_friend", new IsFriendHandler(this));
      table.set("held_item", new HeldItemHandler(this));
      table.set("get_width", new GetWidthHandler(this));
      table.set("get_height", new GetHeightHandler(this));
      table.set("get_position_x", new GetPositionXHandler(this));
      table.set("get_position_y", new GetPositionYHandler(this));
      table.set("get_position_z", new GetPositionZHandler(this));
      table.set("get_position", new GetPositionHandler(this));
      table.set("get_prev_position_x", new GetPrevPositionXHandler(this));
      table.set("get_prev_position_y", new GetPrevPositionYHandler(this));
      table.set("get_prev_position_z", new GetPrevPositionZHandler(this));
      table.set("get_prev_position", new GetPrevPositionHandler(this));
      table.set("get_active_effects", new GetActiveEffectsHandler(this));
      table.set("has_effect", new HasEffectHandler(this));
      table.set("get_effect", new GetEffectHandler(this));
      table.set("get_biome", new GetBiomeHandler(this));
   }
}
