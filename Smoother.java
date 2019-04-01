package testjavacv3;
import javax.swing.JFrame;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.*;
//import org.opencv.core.MatOfRect;

import static org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import org.bytedeco.javacpp.indexer.UByteIndexer;  

import org.bytedeco.javacpp.opencv_features2d.FastFeatureDetector;

import static org.bytedeco.javacpp.opencv_videoio.CvCapture;
import static org.bytedeco.javacpp.opencv_videoio.*;

import org.bytedeco.javacpp.opencv_core.CvSize;  
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core. 
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;

//import org.bytedeco.javacpp.opencv_highgui.*;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import java.io.File;
import java.io.FilenameFilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.IntBuffer;
//import JavaCVUtil;
//spark
/////import spark
import  java.util.*;
//import com.linghushaoxia.image.util.JavaCVUtil;

import org.opencv.core.Core;

//import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.objdetect.CascadeClassifier;



import org.bytedeco.javacpp.indexer.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function; 
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;






public abstract class Smoother 
{
	public static boolean[] KeyTime=new boolean[10000] ;//默认视频时间小于10000秒 
	public static Mat[] MyMat=new Mat[100000] ;//待查图片
	public static int Topofmymat=0 ;//Mymat顶部指针 
	public static double[] Timeofmymat=new double[100000];//Mymat时刻数组
	public static double[] Confiofmymat=new double[100000];//Mymat置信概率数组
	public static int[] Labelofmymat=new int[100000];//Mymat标签数组

   	    public static void main(String[] args) throws Exception 
   	    {
   	    	
   	    	String FilePath="./resource/test3.mp4";
   	    	System.out.println(new Date(System.currentTimeMillis()));//方法一：默认方式输出现在时间  
   	    	MyGrabberFFmpegImage(FilePath,3,2,20);//视频文件路径，每几秒取帧，关键帧±多少秒截取,每几帧取值
   	    	//System.out.println(new Date(System.currentTimeMillis()));//方法一：默认方式输出现在时间  
   	    	startTraining();
   	     //Myrecognizer();
   	    	
   	    /*	
   	    	String testDir="./resource/test/sample.pgm";
   	    	//String testDir="./resource/orl/28/1.pgm";
      	     
   	    	// startTraining();
   	Mat testImage=imread(testDir,CV_LOAD_IMAGE_GRAYSCALE);
   	     String str=JavaCVUtil.MattoString(testImage,20.05); 	
   	     System.out.println(JavaCVUtil.Stringtodouble(str));
   	     Mat M=JavaCVUtil.StringtoMat(str);
   	     //JavaCVUtil.imShow(JavaCVUtil.StringtoMat(str),"test");
   	  JavaCVUtil.imWrite(M, "./resource/test/sample2.pgm");
   	  String resultstr=usexml(M,1);*/
   	  Gospark();
   	 // Mat getM=imgFormat(testImage);
   	   	//startTraining();
   	     //usexml(testImage,1);
   	    	
   	    }
   	 public static void Gospark()
   	 {
   		SparkConf conf = new SparkConf().setMaster("local").setAppName("wc");
     	  JavaSparkContext sc = new JavaSparkContext(conf);
     	 //SparkConf conf=new SparkConf().setAppName("Daniel's first spark app");
    	  
    	  
    	/*  String testDir="./resource/test/sample.pgm";
       	  Mat testImage=imread(testDir,CV_LOAD_IMAGE_GRAYSCALE);
       	     String str=JavaCVUtil.MattoString(testImage,10.25); 	
       	     Mat M=JavaCVUtil.StringtoMat(str);
       	     //JavaCVUtil.imShow(JavaCVUtil.StringtoMat(str),"test");
       	  JavaCVUtil.imWrite(M, "./resource/test/sample2.pgm");
       	     try { 
  				String resultstr=usexml(M,1);
  			} catch (IOException e) 
  			{
  				System.out.println("usexml()方法抛出的异常");//输出异常信息
  			}*/
     	    List list = new ArrayList (); 
	        if (Topofmymat==0)
	        {
	        	System.out.println("No useful picture");
	        	return;
	        }
	        else
	        {
	        	
	        	for (int i=0;i<Topofmymat;i++)
	        		{
	        		
	        			   String str=JavaCVUtil.MattoString(MyMat[i], Timeofmymat[i]);
	        	
	        			
	        			   list.add(str); 
	        			
	        		}
	         }
	        
   	  
  	   
       
         	//list.add(str); 
         	
         	JavaRDD<String> lines = sc.parallelize(list);
  	  
  	  
  	  
  	  
      //JavaRDD<String> lines = sc.textFile("./resource/test.txt");

     JavaRDD<String> shuxing = lines.flatMap(new FlatMapFunction<String, String>() 
     {
        @Override
        public Iterator<String> call(String s) 
        {
      	 Mat calcmat=JavaCVUtil.StringtoMat(s);
      	 double calctime=JavaCVUtil.Stringtodouble(s);
      	String midstr="";
     	 try { 
				midstr=usexml(calcmat,1);
			} catch (IOException e) 
			{
				System.out.println("usexml()方法抛出的异常");//输出异常信息
			}
      	 int count=0;
  		// System.out.println(s);
      	 String[] result=new String[1];
      	
      		 result[0]=midstr+" "+Double.toString(calctime);
      	 
      	 
          return java.util.Arrays.asList(result).iterator();//序号，置信概率，时刻
        }
     });
     JavaRDD<String> confistr = shuxing.filter(new Function<String, Boolean>() {
         public Boolean call(String s) throws Exception {
        	 double confi=Double.parseDouble(s.split(" ")[1]);
        	 if(confi>50)
                     return true;
             return false;
         }
     });
        List<String> output = confistr.collect();
        for (String tuple : output) 
        {
           
          System.out.println(tuple);
          
         
        }
        sc.stop();

      }

    
     
   	 
   	      
   	      


   	    public static void Myrecognizer()
   	    {
   	        if (Topofmymat==0)
   	        {
   	        	System.out.println("No useful picture");
   	        }
   	        else
   	        {
   	        	for (int i=0;i<Topofmymat;i++)
   	        		{
   	        		try { 
   	        				usexml(MyMat[i],i);
   	        			} catch (IOException e) 
   	        			{
   	        				System.out.println("usexml()方法抛出的异常");//输出异常信息
   	        			}
   	        		}
   	        	for (int i=0;i<Topofmymat;i++)
   	        	{
   	        		if (Confiofmymat[i]>20)
   	        		System.out.println("Time:"+Timeofmymat[i]+" Label:"+Labelofmymat[i]+" confidence:"+Confiofmymat[i] );
   	        		
   	        	}
   	        }
   	        
   	    }
   	    
   	 
   	    public static Mat imgFormat(Mat testImage)
   	    {
   	    	//String testDir="./resource/pic1.jpg";
   	    	//Mat testImage=imread(testDir,CV_LOAD_IMAGE_GRAYSCALE);
   	    	Mat M=new Mat(112,92,CV_LOAD_IMAGE_GRAYSCALE);
   	    	resize(testImage, M, M.size());
   	    	cvtColor(M,M,COLOR_BGRA2GRAY);
   	       equalizeHist(M,M);//均衡化直方图  
   	    	//JavaCVUtil.imShow(M,"change");
   	    	return M;
   	    }
   	    public static String usexml(Mat testImage,int count) throws IOException {
   	    	String classifierName = null;
   	    	String testDir="./resource/test/sample.pgm";
   	    	String TRAINING_DATA = "./resource/training.xml";
   	    	//Mat testImage=imread(testDir,CV_LOAD_IMAGE_GRAYSCALE);
   	     int[] predictedLabel=new int[1]  ;
   	     double[] confidence =new double[1];      
   	     FaceRecognizer myrecognizer = EigenFaceRecognizer.create();
      	  myrecognizer.read(TRAINING_DATA);
      	    myrecognizer.predict(testImage, predictedLabel, confidence);
      	  //faceRecognizer.predict(testImage, predictedLabel, confidence);
   	        System.out.println("Predicted label: " + predictedLabel[0]);
   	        System.out.println("confidence: " + confidence[0]);
   	        double percon=Math.max((1-confidence[0]/5000),0)*100;
   	        System.out.println("percon: " + percon+"%");
   	        Confiofmymat[count]=percon;
   	        Labelofmymat[count]=predictedLabel[0];
   	        String str=Double.toString(predictedLabel[0])+" "+Double.toString(percon);
   	        return str;
    	
   	    }
   	    public static void startTraining(){
   	    	String TRAINING_DATA = "./resource/training.xml";
   	        String trainingDir = "./resource/orl";
   	        String testDir="./resource/test/sample2.pgm";
   	        //Mat testImage = imread("resources/test.jpg", CV_LOAD_IMAGE_GRAYSCALE);

   	        File root = new File(trainingDir);

   	        FilenameFilter imgFilter = new FilenameFilter() {
   	            public boolean accept(File dir, String name) {
   	                name = name.toLowerCase();
   	                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
   	            }
   	        };
   	     File[] directoryFiles=root.listFiles();
   	     int FileCount=0;
   	     for (File f:directoryFiles)
	        {
	        	if (f.isDirectory()) {
	                String imagepath=f.getPath();
	                File dir=new File(imagepath);
	                File[] imageFiles = dir.listFiles(imgFilter);
	                FileCount+=imageFiles.length;
	                
	            }
	        }
   	        
   	        
   	        
   	        
   	        
   	       
   	       // File[] imageFiles=new File[FileCount];

   	        MatVector images = new MatVector(FileCount);

   	        Mat labels = new Mat(FileCount, 1, CV_32SC1);
   	     System.out.println("running");
   	        IntBuffer labelsBuf = labels.createBuffer();
   	     
   	        int counter = 0;
   	        for (File f:directoryFiles)
   	        {
   	        	if (f.isDirectory()) {
   	                String imagepath=f.getPath();
   	                File dir=new File(imagepath);
   	                File[] imageFiles2 = dir.listFiles(imgFilter);
   	            
   	          for (File image : imageFiles2) {
   	            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
   	         String b=image.getPath();
   	            String  a[]=b.split("\\\\");
   	         System.out.println(a[a.length-2]);
   	            int label = Integer.parseInt(a[a.length-2]);

   	            images.put(counter, img);

   	            labelsBuf.put(counter, label);

   	            counter++;
   	        }
   	        	}
   	        }
   	        	System.out.println(counter);
   	        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
   	        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
   	     
   	     FaceRecognizer faceRecognizer = EigenFaceRecognizer.create(50,10000);
   	 // faceRecognizer.setThreshold(100);
   	        //FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
   	           	        //faceRecognizer.
   	       // faceRecognizer.create();
   	        faceRecognizer.train(images, labels);
   	        faceRecognizer.save(TRAINING_DATA);
   	        Mat testImage=imread(testDir,CV_LOAD_IMAGE_GRAYSCALE);
   	        
   	     int[] predictedLabel=new int[11]  ;
   	     double[] confidence =new double[11];
   	  FaceRecognizer myrecognizer = EigenFaceRecognizer.create();
   	  myrecognizer.read(TRAINING_DATA);
   	    myrecognizer.predict(testImage, predictedLabel, confidence);
   	  //faceRecognizer.predict(testImage, predictedLabel, confidence);
	        System.out.println("Predicted label: " + predictedLabel[0]);
	       
	        System.out.println("confidence: " + confidence[0]);
   	    }
  	    	/*String classifierName = null;
   	        if (args.length > 0) {
   	            classifierName = args[0];
   	        } else {
   	       //URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
   	       URL url = new URL("file:///I:/Users/wjy/eclipse-workspace/testjavacv3/haarcascade_frontalface_alt.xml");
   	            File file = Loader.extractResource(url, null, "classifier", ".xml");
   	            file.deleteOnExit();
   	            classifierName = file.getAbsolutePath();
   	        }
   	      System.out.println("haarcascade_frontalface_alt.xml");
   	        // Preload the opencv_objdetect module to work around a known bug.
   	        Loader.load(opencv_objdetect.class);
   	   //  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   	  System.load("I:\\opencv\\build\\java\\x64\\opencv_java2411.dll");
   	        // We can "cast" Pointer objects by instantiating a new object of the desired class.
   	     //String a=Smoother.class.getResource("haarcascade_frontalface_alt.xml").getPath();
 	     //System.out.println(a);
 	    //a="I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/haarcascade_frontalface_alt.xml";
 	     //CascadeClassifier faceDetector = new CascadeClassifier(a);
   	     //CascadeClassifier faceDetector=new CascadeClassifier("file:///I:/Users/wjy/eclipse-workspace/testjavacv3/haarcascade_frontalface_alt.xml") ;  	     
   	     CascadeClassifier classifier = new CascadeClassifier(classifierName);
   	     //CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(classifierName));
   	     //   CascadeClassifier classifier = new CascadeClassifier();
   	      if (classifier.isNull()) {
   	            System.err.println("Error loading classifier file \"" + classifierName + "\".");
   	            System.exit(1);
   	        } 
   	      //File c=new File("I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/115.png");
   	//String b=Smoother.class.getResource("115.png").getPath();
   	//b="I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/115.png";
    //System.out.println(b);
   	
       Mat  image = imread("./resource/pic1.jpg", IMREAD_COLOR);  
       Mat grayscr=new Mat();  
       cvtColor(image,grayscr,COLOR_BGRA2GRAY);//摄像头是彩色图像，所以先灰度化下  
       equalizeHist(grayscr,grayscr);//均衡化直方图  
       JavaCVUtil.imShow(grayscr,"gray");
      //MatOfRect faceDetections = new MatOfRect();
   	  RectVector faces=new RectVector();
      
      classifier.detectMultiScale(image, faces);
     // rectangle(image, , new Scalar(0, 0, 255, 1));//在原图上画出人脸的区域  
      
      for(int i=0;i<faces.size();i++)//遍历检测出来的人脸  
      {  
          Rect face_i=faces.get(i);  
          rectangle(image, face_i, new Scalar(0, 0, 255, 1));//在原图上画出人脸的区域  
          Mat cut =new Mat(image,face_i); 
          JavaCVUtil.imShow(cut,"cut"+i);
      }  */
         
      
     // JavaCVUtil.imShow(image,"image");//获取摄像头图像并放到窗口上显示，frame是一帧视频图像    
/*
      System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));        
      for (Rect rect : faceDetections.toArray()) {
          org.opencv.imgproc.Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),                    
         		 new Scalar(0, 255, 0));
      }

      String filename = "./resource/ouput.png";
      System.out.println(String.format("Writing %s", filename));
      Imgcodecs.imwrite(filename, image);
      
   	    /* System.loadLibrary(NATIVE_LIBRARY_NAME);
   	        String classifierName = null;
   	        if (args.length > 0) {
   	            classifierName = args[0];
   	        } else {
   	            URL url = new URL("I:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
   	            File file = Loader.extractResource(url, null, "classifier", ".xml");
   	            file.deleteOnExit();
   	            classifierName = file.getAbsolutePath();
   	        }
*/
   	        // Preload the opencv_objdetect module to work around a known bug.
   	        //Loader.load(opencv_objdetect.class);
   	     
   	        
   	           //CvHaarClassifierCascade classifier = new CvHaarClassifierCascade
   	        //		   (cvLoad("I:\\opencv-3.4.1\\data\\haarcascades\\haarcascade_frontalface_alt.xml"));
   	        
   	     /*      if (classifier.isNull()) {
   	            System.err.println("Error loading classifier file \"" );
   	            System.exit(1);
   	        }
         System.out.println("\nRunning FaceDetector");

         CascadeClassifier faceDetector = new CascadeClassifier(getResource
        		 ("haarcascade_frontalface_alt.xml").getPath());
         System.out.println("\nRunning FaceDetector");*/
         /*
         Mat image = imread(Smoother.class.getResource("shekhar.JPG").getPath());

         MatOfRect faceDetections = new MatOfRect();
         faceDetector.detectMultiScale(image, faceDetections);

         System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));        
         for (Rect rect : faceDetections.toArray()) {
             rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),                    
            		 new Scalar(0, 255, 0));
         }

         String filename = "ouput.png";
         System.out.println(String.format("Writing %s", filename));
         imwrite(filename, image);
         
   	    				String inputFilePth = "./resource/test.avi";
   	    				MyGrabberFFmpegImage(inputFilePth, 5);//文件名；每几秒取1帧图片
   	    				
*/   	    				
   	    				
   	    
   	    public static boolean GetFaceImg(Mat image,String count,boolean mode ) throws IOException
   	    {
  	    	String classifierName = null;
   	        
   	       //URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
   	       URL url = new URL("file:///I:/Users/wjy/eclipse-workspace/testjavacv3/haarcascade_frontalface_alt.xml");
   	            File file = Loader.extractResource(url, null, "classifier", ".xml");
   	            file.deleteOnExit();
   	            classifierName = file.getAbsolutePath();
   	        
   	     // System.out.println("haarcascade_frontalface_alt.xml");
   	        // Preload the opencv_objdetect module to work around a known bug.
   	        Loader.load(opencv_objdetect.class);
   	   //  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   	  System.load("I:\\opencv\\build\\java\\x64\\opencv_java2411.dll");
   	        // We can "cast" Pointer objects by instantiating a new object of the desired class.
   	     //String a=Smoother.class.getResource("haarcascade_frontalface_alt.xml").getPath();
 	     //System.out.println(a);
 	    //a="I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/haarcascade_frontalface_alt.xml";
 	     //CascadeClassifier faceDetector = new CascadeClassifier(a);
   	     //CascadeClassifier faceDetector=new CascadeClassifier("file:///I:/Users/wjy/eclipse-workspace/testjavacv3/haarcascade_frontalface_alt.xml") ;  	     
   	     CascadeClassifier classifier = new CascadeClassifier(classifierName);
   	     //CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(classifierName));
   	     //   CascadeClassifier classifier = new CascadeClassifier();
   	      if (classifier.isNull()) {
   	            System.err.println("Error loading classifier file \"" + classifierName + "\".");
   	            System.exit(1);
   	        } 
   	      //File c=new File("I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/115.png");
   	//String b=Smoother.class.getResource("115.png").getPath();
   	//b="I:/Users/wjy/eclipse-workspace/testjavacv3/bin/testjavacv3/115.png";
    //System.out.println(b);
   	
       //Mat  image = imread("./resource/pic1.jpg", IMREAD_COLOR);  
       Mat grayscr=new Mat();  
       cvtColor(image,grayscr,COLOR_BGRA2GRAY);//摄像头是彩色图像，所以先灰度化下  
       equalizeHist(grayscr,grayscr);//均衡化直方图  
       //JavaCVUtil.imShow(grayscr,"gray");
      //MatOfRect faceDetections = new MatOfRect();
   	  RectVector faces=new RectVector();
      
      classifier.detectMultiScale(grayscr, faces);
      //rectangle(image, , new Scalar(0, 0, 255, 1));//在原图上画出人脸的区域  
      if (faces.size()>0)
      {
	      for(int i=0;i<faces.size();i++)//遍历检测出来的人脸  
	      {  
	          Rect face_i=faces.get(i);  
	          //rectangle(image, face_i, new Scalar(0, 0, 255, 1));//在原图上画出人脸的区域  
	          Mat cut =new Mat(image,face_i); 
	          Mat Myfaceimg=imgFormat(cut);
	          if (mode)
	          {
	        	  MyMat[Topofmymat]=Myfaceimg;
	        	  Timeofmymat[Topofmymat]=Double.parseDouble(count);
	        	  Topofmymat++;
	          }
	          //JavaCVUtil.imShow(image,count+"img"+i);
	          JavaCVUtil.imWrite(Myfaceimg,"./resource/facetotest/"+count+"_"+i+".png");
	          
	      }  
	      return true;
      }
      else
    	  return false;
            
   	    }
    	public static void MyGrabberFFmpegImage(String filePath, int secperimage,double accuracy,int everyf) throws Exception 
    	{
    	        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
    	        //int secperimage=5;	
    	        ff.start();
    	        String name;
    	        double fps=ff.getVideoFrameRate();
    	        System.out.println("fps="+fps); 
                
    	        int ffLength = ff.getLengthInFrames();
    	        //fflength:视频帧数
    	        //List<Integer> randomGrab = random(ffLength, randomSize);
    	        //int maxRandomGrab = randomGrab.get(randomGrab.size() - 1);
    	        Frame f;
    	        int i = 0;
    	        int j=0;
    	        //int count=0;
    	        while (i < ffLength) {
    	        	
    	        	f = ff.grabImage();
    	            
    	        	
    	        	if (i%((int)fps*secperimage)==0)  
    	        			{
    	        				name=String.format("%d",j);
    	             	         Mat videomat=doExecuteFrame(f, name);
    	             	         if (GetFaceImg(videomat,name,false)) 
    	             	         	{
    	             	        	 	KeyTime[j]=true;
    	             	         	}

    	            System.out.println("i="+i+" fflength="+ffLength);
    	            //System.out.println(new Date(System.currentTimeMillis()));//方法一：默认方式输出现在时间  

    	            //i=i+(int)fps*secperimage;
    	            j=j+secperimage;
    	            }
    	        	i++;
    	        }
    	        ff.stop();
    	        
    	        ff.start();
    	         i = 0;
    	         j=0;
    	         int count=0;
    	        while (i < ffLength) {
    	        	
    	        	f = ff.grabImage();
    	            
    	        	
    	        	if (ifkey((int)(i/fps),accuracy))  
    	        			{
    	        				if (count%everyf==0)
    	        				{
    	        				double t=(double)i/fps;
    	        				name=String.format("%.2f",t);
    	             	         Mat videomat=doExecuteFrame(f, name);
    	             	         boolean b=GetFaceImg(videomat,name,true);

    	            System.out.println("i="+i+"fflength="+ffLength);
    	        				}
    	        				count++;
    	        			}
    	           
    	            
    	        	i++;
    	        	}
    	        ff.stop();
	
    	       
    	    }
    		public static boolean ifkey(int tm,double accuracy)
    		{
    			int i=0;
    			boolean result=false;
    			for(i=Math.max(0,(int)(tm-accuracy));i<(int)(tm+accuracy);i++)
    			{
    				if (KeyTime[i]==true)
    					result=true;
    			}
    			return result;
    		}
    		
    		public static Mat doExecuteFrame(Frame f, String index) {
    	        Mat M=new Mat();
    	        
    	    	if (null == f || null == f.image) {
    	            return M;
    	        }
    	        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    	        String imageMat = ".png";
    	        String FileName = "./resource/imgfromvideo/";
    	        opencv_core.Mat mat = converter.convertToMat(f);
    	        opencv_imgcodecs.imwrite(FileName + index +imageMat, mat);//存储图像
    	        return mat;
    	    }

    	    public static List<Integer> random(int baseNum, int length) {
    	        List<Integer> list = new ArrayList<>(length);
    	        while (list.size() < length) {
    	            Integer next = (int) (Math.random() * baseNum);
    	            if (list.contains(next)) {
    	                continue;
    	            }
    	            list.add(next);
    	        }
    	        Collections.sort(list);
    	        return list;
    	    }
    	}
           
          
    	/*
    	CvCapture cvCapture = cvCreateFileCapture("./resource/test2.avi");  
        if (cvCapture !=null) {  
            //获取视频的帧率  
            double fps = cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FPS);  
            //设置矩阵框的尺寸  
            CvSize cvSize =  cvSize(  
                    (int)cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_HEIGHT),   
                    (int)cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_WIDTH)  
                    );  
            //初始化视频输出  
            CvVideoWriter cvVideoWriter = cvCreateVideoWriter(  
                    //输出视频文件名，文件路径  
                    "resources/beautifulOut.avi",   
                    //编码格式：  
                    CV_FOURCC((byte)'M', (byte)'J', (byte)'P', (byte)'G'),   
                    //帧率  
                    fps,   
                    //帧尺寸  
                    cvSize);  
            //创建视频输出帧的图像大小、位深度、通道数  
            IplImage polarImage = cvCreateImage(cvSize, IPL_DEPTH_8U, 3);  
            IplImage bgrImage ;  
            while((bgrImage = cvQueryFrame(cvCapture)) !=null){  
                //对每帧图像进行处理  
                cvLogPolar(  
                        //原始图像  
                        bgrImage,   
                        //输出图像  
                        polarImage,   
                        //以图像中心为原点进行变换  
                        cvPoint2D32f(bgrImage.width()/2, bgrImage.height()/2),  
                        //缩放比例100  
                        100,  
                        //  
                        CV_INTER_LINEAR | CV_WARP_FILL_OUTLIERS|CV_WARP_INVERSE_MAP);  
                //将每一帧图像保存到视频流中  
                cvWriteFrame(cvVideoWriter, polarImage);  
            }  
            //释放资源  
            cvReleaseVideoWriter(cvVideoWriter);  
            cvReleaseImage(polarImage);  
            cvReleaseCapture(cvCapture);  
            
        }  
        else
        {
        System.out.println("running");
        }
          /*  //图片路径  
            String filePath = "./resource/pic1.jpg";  
            // 以彩色模式读取图像  
            Mat image = JavaCVUtil.imRead(filePath, IMREAD_COLOR);  
            //原始图像  
            JavaCVUtil.imShow(image,"原始图片");  
            //对图像加盐  
            Mat dest = salt(image, 2000);  
            // 显示图像  
            JavaCVUtil.imShow(dest, "加盐处理");  
          */
           
           /** 
            *  
            * 功能说明:对图片加盐，添加噪点 
            * @param image 
            * 原始图片 
            * @param n 
            * 噪点数量 
            * @return Mat 
            * @time:2016年4月19日下午8:40:27 
            * @author:linghushaoxia 
            * @exception: 
            * 
            */  
    /*        
    public static Mat salt(Mat image, int n) {  
            // 随机数生成对象  
            Random random = new Random();*/  
            /** 
             * 无符号字节索引，访问Mat结构的元素 
             * 访问Mat高效便捷 
             */  
         /*   UByteIndexer indexer = image.createIndexer();  
            //图像通道  
            int nbChannels = image.channels();  
            //加盐数量  
            for (int i = 0; i < n; i++) {  
                /** 
                 * 获取随机行、列 
                 * 噪点随机分布 
                 */  
           /*     int row = random.nextInt(image.rows());  
                int col = random.nextInt(image.cols());  
                //处理全部通道数据，均进行加盐，设置为最大值255  
                for (int channel = 0; channel < nbChannels; channel++) {  
                indexer.put(row, col, channel, 255);  
                }  
            }  
            return image;  
            }  
          
          
        } 

    /*
    	
    	
    	
        Mat  image = imread("./resource/pic1.png", IMREAD_COLOR);  
        if (image==null||image.empty()) {  
          System.out.println("读取图像失败，图像为空");  
          return;  
        }  
    
        System.out.println("图像宽x高" + image.cols() + " x " + image.rows());  

        JavaCVUtil.imShow(image, "原始图像");  
        //创建空mat，保存处理图像  
        Mat result = new Mat();  
        int flipCode=1;  

        flip(image, result, flipCode);  
        //显示处理过的图像  
        JavaCVUtil.imShow(result, "水平翻转");  

        JavaCVUtil.imWrite(result, "./resource/lakeResult.jpg");  
    
      //克隆图像  
        Mat imageCircle = image.clone();  

        circle(imageCircle, // 目标图像  
          new Point(420, 150), // 圆心坐标  
          65, // radius  
          new Scalar(0,200,0,0), // 颜色，绿色  
          2, // 线宽  
          8, // 8-connected line  
          0); // shift  
    
        opencv_imgproc.putText(imageCircle, //目标图像  
          "Lake and Tower", // 文本内容(不可包含中文)  
          new Point(460, 200), // 文本起始位置坐标  
          FONT_HERSHEY_PLAIN, // 字体类型  
          2.0, // 字号大小  
          new Scalar(0,255,0,3), //文本颜色，绿色  
          1, // 文本字体线宽  
          8, // 线形.  
          false); //控制文本走向  
        JavaCVUtil.imShow(imageCircle, "画圆mark"); 
 
    }*/
