# Prasunet_AD_05
It is a QR code scanner app that allows users to scan QR codes using their device's camera. Upon scanning, the app extracts the information encoded in the QR code and displays it on the screen. Additionally, it provides an option to open links or perform specific actions based on the scanned content. 
<br>
Author- Srijita Dasgupta

Explanation:

1. Dependencies:
   - com.google.zxing libraries (core and javase) are used for QR code decoding.

2. Swing Components:
   - JFrame, JPanel, JLabel, and JButton are used to create the main window, camera preview, status label, and scan button.

3. Webcam Integration:
   - Webcam class from Webcam-Capture library is used to access the camera and capture frames.

4. QR Code Scanning:
   - scanQRCode method takes a BufferedImage, converts it to a LuminanceSource, and decodes using MultiFormatReader from zxing.

5. Start/Stop Scan:
   - startScan method initializes the webcam, starts capturing frames, and processes them for QR codes.
   - stopScan method closes the webcam and stops scanning.

6. Display Result:
   - displayScannedResult method displays the decoded QR code information in a JOptionPane.
   - You can extend this method to perform additional actions based on the scanned content (e.g., open a URL, execute a command).

7. Main Method:
   - The main method initializes the QRCodeScannerApp on the Event Dispatch Thread (EDT) using SwingUtilities.invokeLater() for thread safety.

Running the Application:

1. Setup:
   - Include the zxing libraries in your project (either download from Maven Central or add via build system).
   - Make sure to include the Webcam-Capture library for webcam access (Webcam.getDefault()).

2. Compile and Run:
   - Compile QRCodeScannerApp.java using javac QRCodeScannerApp.java.
   - Run the compiled class using java QRCodeScannerApp.

3. Usage:
   - Click on "Start Scan" button to initialize the camera and start scanning for QR codes.
   - Hold a QR code in front of the camera. Once detected, it will decode and display the scanned information in a dialog.
   - Use the dialog to perform any additional actions based on the scanned content.

This implementation provides a basic QR code scanner app in Java using Swing for GUI and zxing for QR code decoding, allowing users to scan QR codes using their device's camera and display the decoded information.
