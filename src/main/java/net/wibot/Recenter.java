package net.wibot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Recenter implements ModInitializer {

	//Console logger
    public static final Logger LOGGER = LoggerFactory.getLogger("recenter");

	public static KeyBinding recenter_keybinding;

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing Recenter!");

		recenter_keybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.recenter.recenter_camera", // The translation key of the keybindings name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_V, // The keycode of the key
				"category.recenter.name" // The translation key of the keybindings category.
		));
	}
}