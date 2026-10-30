package com.moon.scripting.api.functions;

import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.types.Block;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.api.types.Vec3d;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;

@com.moon.scripting.documentation.annotation.FunctionContainer(value = "world", path = "functions/world")
@Description("Contains functions to control the local world")
public interface WorldFunctions {
   @Description("Removes the given entity id from the world")
   void remove(@Parameter("entity_id") @Description("The id of the entity to be removed") int entityId);

   @Description("Returns the block at the given coordinates")
   Block get_block_at(
      @Parameter("x") @Description("The x coordinate") double x,
      @Parameter("y") @Description("The y coordinate") double y,
      @Parameter("z") @Description("The z coordinate") double z
   );

   @Description("Returns the block at the given vector coordinates")
   Block get_block_at_vec(@Parameter("vec") @Description("The vector coordinates") Vec3d vec3d);

   @Description("Returns an array of all loaded entity ids")
   int[] get_entities();

   @Description("Returns an array of all loaded player ids")
   int[] get_players();

   @Description("Returns the name of the given entity id")
   String get_name(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the display name of the given entity id")
   String get_display_name(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current hurt-time of the given entity id")
   int get_hurt_time(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current hurt resistant time of the given entity id")
   int get_hurt_resistant_time(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current health of the given entity id")
   double get_health(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current max health of the given entity id")
   double get_max_health(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current sprinting state of the given entity id")
   boolean is_sprinting(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current sneaking state of the given entity id")
   boolean is_sneaking(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current invisibility state of the given entity id")
   boolean is_invisible(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current burning state of the given entity id")
   boolean is_burning(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current riding state of the given entity id")
   boolean is_riding(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current facing of the given entity id")
   Facing get_facing(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current yaw of the given entity id")
   float get_yaw(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current pitch of the given entity id")
   float get_pitch(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current ticks existed of the given entity id")
   int get_ticks_existed(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns if the given entity id is a player")
   boolean is_player(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns if the given entity id id is a bot")
   boolean is_bot(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns if the given entity id is a target")
   boolean is_target(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns if the given entity id is a friend")
   boolean is_friend(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the item the entity is holding")
   String held_item(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current width of the given entity id")
   float get_width(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current height of the given entity id")
   float get_height(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current position x of the given entity id")
   double get_position_x(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current position y of the given entity id")
   double get_position_y(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current position z of the given entity id")
   double get_position_z(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current position of the given entity id")
   Vec3d get_position(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the previous position x of the given entity id")
   double get_prev_position_x(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the previous position y of the given entity id")
   double get_prev_position_y(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the previous position z of the given entity id")
   double get_prev_position_z(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns the current position of the given entity id")
   Vec3d get_prev_position(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns all current potion effects of the given entity id")
   Potion[] get_active_effects(@Parameter("entity_id") @Description("The entity id") int entityId);

   @Description("Returns if the given entity id has a given effect")
   boolean has_effect(@Parameter("entity_id") @Description("The entity id") int entityId, @Parameter("id") @Description("The id of the potion") int effectId);

   @Description("Returns a specific potion effect if active otherwise returns nil of the given entity id")
   Potion get_effect(@Parameter("entity_id") @Description("The entity id") int entityId, @Parameter("id") @Description("The id of the potion") int effectId);

   @Description("Returns the biome of the current world")
   String get_biome();
}
