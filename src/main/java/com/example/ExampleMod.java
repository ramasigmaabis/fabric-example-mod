package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExampleMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientReceiveMessageEvents.CHAT.register((message, signed, sender, params, time) -> {
            String chat = message.getString();
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            // Pola buat nangkep kode di dalam kurung ( )
            Pattern p = Pattern.compile("\\(([^\\s\\)]+)\\)");
            Matcher m = p.matcher(chat);

            if (m.find()) {
                String soal = m.group(1);
                // Jeda 1.2 detik biar gak dikira bot
                new Thread(() -> {
                    try {
                        Thread.sleep(1200);
                        client.execute(() -> {
                            if (client.player != null) client.player.networkHandler.sendChatMessage(soal);
                        });
                    } catch (Exception e) {}
                }).start();
            }
        });
    }
}
