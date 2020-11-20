package circle_detection;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CircleDetection {
	public void run(String args[]) {
		
	    	Imgcodecs imageCodecs = new Imgcodecs(); 
		
	    	/* Loading the image */
		String default_file = "img\\";
		String image_file = ((args.length > 0) ? default_file+args[0] : default_file+"test1.jpg");

		Mat matrix = Imgcodecs.imread(image_file);
		// Check if image is loaded fine
		if( matrix.empty() ) {
		    System.out.println("Error opening image!");
		    System.exit(-1);
		}
		while(matrix.height()>420) {
			Size sz = new Size(matrix.height()/2,matrix.width()/2);
			Imgproc.resize( matrix, matrix, sz );
		}
		Mat gray = new Mat();
		Imgproc.cvtColor(matrix, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.medianBlur(gray, gray, 5);
		//HighGui.imshow("gray-scaled-image", gray);

		Mat circles = new Mat();

		Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0,(double)gray.rows()/16, 100.0, 40.0, 1, 100);

		for (int x = 0; x < circles.cols(); x++) {
		    double[] c = circles.get(0, x);
		    org.opencv.core.Point center = new Point(Math.round(c[0]), Math.round(c[1]));
		    Imgproc.circle(matrix, center, 1, new Scalar(0,100,100), 3, 8, 0 ); //center
		    int radius = (int) Math.round(c[2]); //radius
		    Imgproc.circle(matrix, center, radius, new Scalar(0,0,255), 3, 8, 0 );
		}

		HighGui.imshow("Circles", matrix);
		HighGui.waitKey();
		System.exit(0);
	}
	public static void main(String[] args) {
		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new CircleDetection().run(args);
	}
}
