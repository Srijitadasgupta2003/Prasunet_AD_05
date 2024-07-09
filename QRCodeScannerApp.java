package Prasunet_AD_05;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import javax.naming.NameAlreadyBoundException;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;

@SuppressWarnings("hiding")
public class QRCodeScannerApp<webcam, webcam, webcam, webcam> {
    private JFrame frame;
    private JLabel statusLabel;
    private JPanel cameraPanel;
    private JButton scanButton;
    private Webcam webcam;
    private boolean isScanning;

    public QRCodeScannerApp(JFrame frame, JLabel statusLabel, JPanel cameraPanel, JButton scanButton, Webcam webcam,
            boolean isScanning) {
        this.frame = frame;
        this.statusLabel = statusLabel;
        this.cameraPanel = cameraPanel;
        this.scanButton = scanButton;
        this.webcam = webcam;
        this.isScanning = isScanning;
    }

    public QRCodeScannerApp() {
        frame = new JFrame("QR Code Scanner App");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cameraPanel = new JPanel();
        statusLabel = new JLabel("Scan QR Code", JLabel.CENTER);
        scanButton = new JButton("Start Scan");

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isScanning) {
                    startScan();
                } else {
                    stopScan();
                }
            }
        });

        frame.add(cameraPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(scanButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startScan() {
        isScanning = true;
        scanButton.setText("Stop Scan");

        // Initialize webcam and start capture
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        // Start a new thread to continuously capture and process frames
        Thread captureThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isScanning) {
                    BufferedImage image = webcam.getImage();
                    Result result = scanQRCode(image);
                    if (result != null) {
                        displayScannedResult(result.getText());
                        stopScan(); // Stop scanning after successful scan
                    }
                }
            }
        });
        captureThread.start();
    }
    private void stopScan() {
        isScanning = false;
        scanButton.setText("Start Scan");

        if (webcam != null) {
            webcam.close();
        }
    }

    private Result scanQRCode(BufferedImage image) {
        // Convert BufferedImage to LuminanceSource
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        // Decode QR code
        Result result = null;
        try {
            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE); // Disable searching for multiple QR codes in the image
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (NameAlreadyBoundException e) {
            // QR code not found
        }
        return result;
    }

    private void displayScannedResult(final String resultText) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(frame, "QR Code Scanned:\n" + resultText);
                // Here you can implement further actions based on the scanned result
                // For example, open a link or perform some specific actions
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @SuppressWarnings("rawtypes")
            @Override
            public void run() {
                new QRCodeScannerApp();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public JPanel getCameraPanel() {
        return cameraPanel;
    }

    public void setCameraPanel(JPanel cameraPanel) {
        this.cameraPanel = cameraPanel;
    }

    public JButton getScanButton() {
        return scanButton;
    }

    public void setScanButton(JButton scanButton) {
        this.scanButton = scanButton;
    }

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public boolean isScanning() {
        return isScanning;
    }

    public void setScanning(boolean isScanning) {
        this.isScanning = isScanning;
    }
}

