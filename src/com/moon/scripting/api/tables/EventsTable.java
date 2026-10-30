package com.moon.scripting.api.tables;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;
import com.moon.scripting.documentation.annotation.TableInfoContainer;

@TableInfoContainer(value = "events", path = "tables/events")
@Description(
   "Contains all events you can hook while creating a module table.<br>**BEWARE:** If an event has any parameters, you need to add a generalized event argument to your function, NOT THE PARAMETERS THEMSELVES. You can access all the event parameters from the argument!"
)
public interface EventsTable {
   @Description("Called once the module is enabled")
   void on_enable();

   @Description("Called once the module is disabled")
   void on_disable();

   @Description("Called on client tick (every 0.05s)")
   void on_tick(@Parameter("pre") @Description("Defines if the event was called pre or post tick") boolean pre);

   @Description("Called before and after client position update, this is done once every tick.")
   void on_update(
      @Parameter("pre") @Description(
   "Whether the event was invoked before or after sending the position updates to the server. Keep in mind position, rotation and ground attributes can only be modified when this is true, otherwise changes will be ignored"
) boolean pre,
      @Parameter("x") @Description("The x position sent to the server") double x,
      @Parameter("y") @Description("The y position sent to the server") double y,
      @Parameter("z") @Description("The z position sent to the server") double z,
      @Parameter("yaw") @Description("The yaw rotation sent to the server (Will update the third-person rotations once modified)") float yaw,
      @Parameter("pitch") @Description("The pitch rotation sent to the server (Will update the third-person rotations once modified)") float pitch,
      @Parameter("ground") @Description("The ground state sent to the server") boolean ground
   );

   @Description("Called before the movement of the player gets applied<br>Mainly used to make the player strafe or move faster")
   void on_move(
      @Parameter("x") @Description("The x motion used to apply the move") double x,
      @Parameter("y") @Description("The y motion used to apply the move") double y,
      @Parameter("z") @Description("The z motion used to apply the move") double z
   );

   @Description(
      "Called once the client sends a packet to the server.<br>Additional parameters are available for specific packets.<br>Editing is currently supported for the following packets: 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x11, 0x13, 0x14, 0x18, 0x19, 0x0B, 0x0C, 0x0E, 0x0F"
   )
   void on_packet_send(
      @Parameter("packet_id") @Description("The packet id") int packetId,
      @Parameter("cancel") @Description("Whether the packet sending should be canceled or not") boolean cancel
   );

   @Description(
      "Called once the client receives a packet from the server.<br>Additional parameters are available for specific packets.<br>Editing is currently supported for the following packets: 0x00, 0x08, 0x14, 0x27, 0x32, 0x48<br>Only reading additionally is supported for the following packets: 0x02"
   )
   void on_packet_receive(
      @Parameter("packet_id") @Description("The packet id") int packetId,
      @Parameter("cancel") @Description("Whether the packet processing should be canceled or not") boolean cancel
   );

   @Description("Called once the overlay of the client is being rendered")
   void on_overlay_render(
      @Parameter("partial_ticks") @Description("The partial ticks") float partialTicks,
      @Parameter("pre") @Description("Whether its pre or post overlay render") boolean pre
   );

   @Description("Called once the world is rendered")
   void on_world_render(@Parameter("partial_ticks") @Description("The partial ticks") float partialTicks);

   @Description("Called once any blur effects are rendered (shadow, blur)")
   void on_effect_render(
      @Parameter("blur") @Description("Whether the blur or shadow is called") boolean blur,
      @Parameter("partial_ticks") @Description("The partial ticks") float partialTicks
   );
}
