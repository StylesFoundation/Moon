package com.moon.scripting.api.functions;

import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.api.types.RayCastResult;
import com.moon.scripting.api.types.Vec2f;
import com.moon.scripting.api.types.Vec3d;
import com.moon.scripting.api.types.Vec3i;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;
import org.luaj.vm2.LuaTable;

@com.moon.scripting.documentation.annotation.FunctionContainer(value = "player", path = "functions/player")
@Description("Contains functions to control the local player")
public interface PlayerFunctions {
   @Description("Places a block and returns if it was actually placed")
   boolean place_block(
      @Parameter("slot") @Description("The slot number you are holding (any others will not work properly)") int slot,
      @Parameter("block_pos") @Description("The position of the block to place on") Vec3i blockPos,
      @Parameter("hit_vec") @Description("The hit vector") Vec3d hitVec,
      @Parameter("facing") @Description("The facing placed on") Facing facing
   );

   @Description("Fires a ray-cast with the given parameters and returns the ray-cast result")
   RayCastResult ray_cast(
      @Parameter("yaw") @Description("The yaw used to fire the ray-cast") float yaw,
      @Parameter("pitch") @Description("The pitch used to fire the ray-cast") float pitch,
      @Parameter("reach") @Description("The reach used to fire the ray-cast") float reach
   );

   @Description("Sets the players client-side yaw")
   void set_yaw(@Parameter("yaw") @Description("The new yaw") float yaw);

   @Description("Sets the players client-side pitch")
   void set_pitch(@Parameter("pitch") @Description("The new pitch") float pitch);

   @Description("Sets the players hot-bar slot")
   void set_held_item(@Parameter("slot") @Description("The new slot") int slot);

   @Description("Sets the speed of the player using the move event")
   void set_speed(
      @Parameter("event") @Description("The move event (if you want to set the speed outside of the move event, use **set_motion_speed**)") LuaTable event,
      @Parameter("speed") @Description("The speed to move with") double speed
   );

   @Description("Sets the speed of the player using motions")
   void set_motion_speed(@Parameter("speed") @Description("The speed to move with") double speed);

   @Description("Sets the players sprinting state")
   void set_sprinting(@Parameter("sprinting") @Description("The new sprinting state") boolean sprinting);

   @Description("Sets the players motion x")
   void set_motion_x(@Parameter("x") @Description("The new motion x") double x);

   @Description("Sets the players motion y")
   void set_motion_y(@Parameter("y") @Description("The new motion y") double y);

   @Description("Sets the players motion z")
   void set_motion_z(@Parameter("z") @Description("The new motion z") double z);

   @Description("Sets the players motion")
   void set_motion(@Parameter("motion") @Description("The new motion as a vector") Vec3d motion);

   @Description("Sets the players position")
   void set_position(@Parameter("position") @Description("The new position as a vector") Vec3d position);

   @Description("Sets the players position and updates the last positions")
   void set_position_and_update(@Parameter("position") @Description("The new position as a vector") Vec3d position);

   @Description("Sets the sneaking state, this utilizes key-presses, make sure to use revert states properly")
   void set_sneaking(@Parameter("state") @Description("The state of sneaking") boolean state);

   @Description(
      "Sends a packet to the server<br>The packets currently supported: 0x00, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x0A, 0x0B, 0x0F<br>Always refer to https://wiki.vg/index.php?title=Protocol&oldid=7368#Serverbound_2 when working with packets!"
   )
   void send_packet(@Parameter("id") @Description("The packet id") int id, @Parameter("arguments") @Description("The packet arguments") Object[] arguments);

   @Description("Sends a packet to the server without causing any other modules to process them")
   void send_packet_no_event(
      @Parameter("id") @Description("The packet id") int id, @Parameter("arguments") @Description("The packet arguments") Object[] arguments
   );

   @Description("Calculates the distance between the given coordinates and the player position")
   double distance_to(
      @Parameter("x") @Description("The x coordinate") float x,
      @Parameter("y") @Description("The y coordinate") float y,
      @Parameter("z") @Description("The z coordinate") float z
   );

   @Description("Calculates the distance between the given vector and the player position")
   double distance_to_vec(@Parameter("vec") @Description("The target vector") Vec3d vec);

   @Description("Calculates the distance between the given entity and the player")
   double distance_to_entity(@Parameter("entity_id") @Description("The target entity's id") int entityId);

   @Description("Calculates the yaw and pitch for looking at the given position")
   Vec2f rotations_to(@Parameter("position") @Description("The position") Vec3d position);

   @Description("Makes the player jump")
   void jump();

   @Description("Clicks the left mouse button")
   void left_click();

   @Description("Clicks the right mouse button")
   void right_click();

   @Description("Swings the players item")
   void swing_item();

   @Description("Uses the current held item")
   void use_item();

   @Description("Returns whether the player is on edge or not")
   boolean is_on_edge();

   @Description("Returns the current forward and strafe the player is moving at")
   Vec2f get_input();

   @Description("Returns the current players yaw")
   float get_yaw();

   @Description("Returns the current players pitch")
   float get_pitch();

   @Description("Returns the last players yaw")
   float get_prev_yaw();

   @Description("Returns the last players pitch")
   float get_prev_pitch();

   @Description("Returns the players current motion x")
   double get_motion_x();

   @Description("Returns the players current motion z")
   double get_motion_y();

   @Description("Returns the players current motion y")
   double get_motion_z();

   @Description("Returns the players current motion")
   Vec3d get_motion();

   @Description("Returns the players current position x")
   double get_position_x();

   @Description("Returns the players current position z")
   double get_position_y();

   @Description("Returns the players current position y")
   double get_position_z();

   @Description("Returns the players current position")
   Vec3d get_position();

   @Description("Returns the players previous position x")
   double get_prev_position_x();

   @Description("Returns the players previous position z")
   double get_prev_position_y();

   @Description("Returns the players previous position y")
   double get_prev_position_z();

   @Description("Returns the players previous position")
   Vec3d get_prev_position();

   @Description("Returns if the player is moving")
   boolean is_moving();

   @Description("Returns if the player is on ground")
   boolean is_on_ground();

   @Description("Returns if the player is sprinting")
   boolean is_sprinting();

   @Description("Returns if the player is using the item")
   boolean is_using_item();

   @Description("Returns if the player is blocking")
   boolean is_blocking();

   @Description("Returns if the player is in water")
   boolean is_in_water();

   @Description("Returns if the player is in lava")
   boolean is_in_lava();

   @Description("Returns if the player is on a ladder")
   boolean is_on_ladder();

   @Description("Returns if the player is burning")
   boolean is_burning();

   @Description("Returns if the player is sneaking")
   boolean is_sneaking();

   @Description("Returns if the player is in a cobweb")
   boolean is_in_cobweb();

   @Description("Returns if the player is dead")
   boolean is_dead();

   @Description("Returns if the player is riding a vehicle")
   boolean is_riding();

   @Description("Returns if the player is currently colliding with anything vertically")
   boolean collided_vertically();

   @Description("Returns if the player is currently colliding with anything horizontally")
   boolean collided_horizontally();

   @Description("Returns the players current ticks existed")
   int get_ticks_existed();

   @Description("Returns the players current speed")
   double get_speed();

   @Description("Returns the base move speed")
   double get_base_move_speed();

   @Description("Returns the players current fall distance")
   double get_fall_distance();

   @Description("Returns the players current health")
   double get_health();

   @Description("Returns the players max health")
   double get_max_health();

   @Description("Returns the players current absorption hearts")
   double get_absorption();

   @Description("Returns the players current food amount")
   double get_food_amount();

   @Description("Returns the players current hurt-time")
   int get_hurt_time();

   @Description("Returns the players currently held item")
   int get_held_item_slot();

   @Description("Returns the players current name")
   String get_name();

   @Description("Returns the players entity id")
   int get_id();

   @Description("Returns the players facing<br>For reference please click [here](../enums/facing.md)")
   Facing get_facing();

   @Description("Returns the current minecraft ray-cast result used for all in-game actions")
   RayCastResult get_mouse_over();

   @Description("Returns all active potion effects")
   Potion[] get_active_effects();

   @Description("Returns if the player has the given potion effect")
   boolean has_effect(@Parameter("id") @Description("The id of the potion") int id);

   @Description("Returns a specific potion effect if active otherwise returns nil")
   Potion get_effect(@Parameter("id") @Description("The id of the potion") int id);
}
