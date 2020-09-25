package dev._2lstudios.arkflamestore.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import dev._2lstudios.arkflamestore.util.CommandUtil;

public class StoreRunnable implements Runnable {
    private final Logger logger;
    private final CommandUtil commandUtil;
    private final InetAddress address;
    private final int port;
    private final int reconnectMillis;
    private Socket socket;
    private boolean killed = false;

    public StoreRunnable(final Logger logger, final CommandUtil commandUtil, final String address, final int port,
            final int reconnectMillis) throws UnknownHostException {
        this.logger = logger;
        this.commandUtil = commandUtil;
        this.address = InetAddress.getByName(address);
        this.port = port;
        this.reconnectMillis = reconnectMillis;
    }

    @Override
    public void run() {
        while (!killed) {
            try (final Socket socket = new Socket(address, port)) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;

                this.socket = socket;

                logger.info("Listening on " + address.getHostAddress() + ":" + port);

                while ((line = in.readLine()) != null) {
                    final String[] splittedLine = line.split("[,]");

                    if (splittedLine.length > 1) {
                        commandUtil.executeCommand(splittedLine);
                    } else {
                        commandUtil.executeCommand(line);
                    }
                }
            } catch (final IOException e) {
                // Ignore
            } finally {
                logger.info("Disconnected from " + address.getHostAddress() + ":" + port);
            }

            synchronized (this) {
                try {
                    wait(reconnectMillis);
                } catch (final InterruptedException e) {
                    // Ignored
                }
            }
        }
    }

    public void flush() {
        write("\n");
    }

    public void write(final String string) {
        if (socket != null && !socket.isClosed()) {
            try {
                new DataOutputStream(socket.getOutputStream()).writeUTF(string);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill() {
        killed = true;

        close();
    }

    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (final IOException e) {
                // Ignore
            }
        }
    }
}