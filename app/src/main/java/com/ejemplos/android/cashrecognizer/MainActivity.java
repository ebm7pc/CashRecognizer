package com.ejemplos.android.cashrecognizer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase cameraView = null;
    private static boolean initOpenCV = false;

    /**
     * metodo para inicializar la libreria de opencv
     */
    static { initOpenCV = OpenCVLoader.initDebug(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (CameraBridgeViewBase) findViewById(R.id.cameraview);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);

    }

    /**
     * habilitar componente
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (initOpenCV) { cameraView.enableView(); }
    }

    /**
     * deshabilitar componente
     */
    @Override
    public void onPause() {
        super.onPause();

        // Release the camera.
        if (cameraView != null) {
            cameraView.disableView();
            cameraView = null;
        }
    }

    /**
     * detectar cuando la captura se inicia
     * @param width -  the width of the frames that will be delivered
     * @param height - the height of the frames that will be delivered
     */
    @Override
    public void onCameraViewStarted(int width, int height) { }

    /**
     * detectar cuando la captura se detiene
     */
    @Override
    public void onCameraViewStopped() { }

    /**
     * procesar cada cuadro capturado
     * @param inputFrame cuadro a ser analizado
     * @return
     */
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat src = inputFrame.gray(); // convertir a escala de grises
        Mat cannyEdges = new Mat();  // objeto para almacenar el resultado

        // aplicar el algoritmo canny para detectar los bordes
        Imgproc.Canny(src, cannyEdges, 10, 100);

        // devolver el objeto Mat procesado
        return cannyEdges;
        //return inputFrame.rgba();
    }

}