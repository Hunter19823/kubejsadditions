package pie.ilikepiefoo.fabric;

public class FabricEventsJS {
	// Custom Fabric Event
	public static final String FABRIC_EVENT_REGISTER = "fabric.event.register";

	// World Render Events
	public static final String CLIENT_BEFORE_ENTITIES = "fabric.client.before_entities";
	public static final String CLIENT_AFTER_TRANSLUCENT = "fabric.client.after_translucent";
	public static final String CLIENT_AFTER_ENTITIES = "fabric.client.after_entities";
	public static final String CLIENT_AFTER_SETUP = "fabric.client.after_setup";
	public static final String CLIENT_START_RENDER = "fabric.client.start_render";
	public static final String CLIENT_LAST_RENDER = "fabric.client.last_render";
	public static final String CLIENT_END_RENDER = "fabric.client.end_render";
	public static final String CLIENT_BEFORE_BLOCK_OUTLINE = "fabric.client.before_block_outline";
	public static final String CLIENT_BLOCK_OUTLINE = "fabric.client.block_outline";

	// Hud Events
	public static final String CLIENT_RENDER_HUD = "fabric.client.render_hud";

	// Elytra Events
	public static final String ALLOW_ELYTRA_FLIGHT = "fabric.allow_elytra_flight";
	public static final String CUSTOM_ELYTRA_FLIGHT = "fabric.custom_elytra_flight";

	// Sleep Events
	public static final String ALLOW_SLEEPING = "fabric.allow_sleeping";
	public static final String START_SLEEPING = "fabric.start_sleeping";
	public static final String STOP_SLEEPING = "fabric.stop_sleeping";
	public static final String ALLOW_BED = "fabric.allow_bed";
	public static final String ALLOW_SLEEP_TIME = "fabric.allow_sleep_time";
	public static final String ALLOW_NEARBY_MONSTERS = "fabric.allow_nearby_monsters";
	public static final String ALLOW_RESETTING_TIME = "fabric.allow_resetting_time";
	public static final String MODIFY_SLEEPING_DIRECTION = "fabric.modify_sleeping_direction";
	public static final String ALLOW_SETTING_SPAWN = "fabric.allow_setting_spawn";
	public static final String SET_BED_OCCUPATION_STATE = "fabric.set_bed_occupation_state";
	public static final String MODIFY_WAKE_UP_POSITION = "fabric.modify_wake_up_position";


}

