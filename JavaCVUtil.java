package testjavacv3;
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
import javax.imageio.ImageIO;  
import javax.swing.JFrame;  
  
import org.bytedeco.javacpp.opencv_core.Mat;  
import org.bytedeco.javacpp.opencv_imgcodecs;  
import org.bytedeco.javacv.CanvasFrame;  
import org.bytedeco.javacv.Frame;  
import org.bytedeco.javacv.Java2DFrameConverter;  
import org.bytedeco.javacv.OpenCVFrameConverter;  
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;  
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
  
/**����˵����JavaCV������ 
 * @author:linghushaoxia 
 * @time:2016��3��31������8:49:25 
 * @version:1.0 
 * Ϊ�й������ļ���, 
 * ����һƬ������ǿ�����! 
 *  
 */  
public class JavaCVUtil {  
    /** 
     *  
     * ����˵��:��ʾͼ�� 
     
     */  
    public static void imShow(Mat mat,String title) {  
        //opencv�Դ�����ʾģ�飬��ƽ̨��Ƿ�ѣ�תΪJava2Dͼ�����ͽ�����ʾ  ��������е����⣬���ã�
      ToMat converter = new OpenCVFrameConverter.ToMat();  
      CanvasFrame canvas = new CanvasFrame(title, 1);  
      canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      canvas.showImage(converter.convert(mat));  
        
    }  
    public static Mat imRead(String filePath,int i) {  
        //opencv�Դ�����ʾģ�飬��ƽ̨��Ƿ�ѣ�תΪJava2Dͼ�����ͽ�����ʾ  
        Mat  image = imread(filePath, i);  
        return image;
    }  
    
    /** 
     *  
     * ����˵��:MatתString
     
     * 
     */  
    	public static double Stringtodouble(String mystr)
    	{	    
    		String [] temp=mystr.split("\n");
    		return Double.parseDouble(temp[0].split(" ")[2]);
    	}
	    public static Mat StringtoMat(String mystr)
	    {
	    	String [] temp=mystr.split("\n");
	    	/*for (int i=0;i<temp.length;i++)
	    		System.out.println(temp[i]);*/
	    	int row=Integer.parseInt(temp[0].split(" ")[0]);
	    	int col=Integer.parseInt(temp[0].split(" ")[1]);
	    	
	    	int mybyte [][]=new int[row][col];
	    	for (int i=1;i<=row;i++)
	    	{
	    		for(int j=0;j<col;j++)
	    		{
	    			String s=(temp[i].split(" "))[j];
	    			//System.out.println(s);
	    			mybyte[i-1][j]=Integer.parseInt((temp[i].split(" "))[j]);
	    		}
	    	}
	    	
	    	byte mybyte1[]=new byte [row*col];
	    	for (int i=1;i<=row;i++)
	    	{
	    		for(int j=0;j<col;j++)
	    	  	mybyte1[(i-1)*col+j]=(byte)Integer.parseInt(temp[i].split(" ")[j]);
    		
	    	}
	    	//for (int i=0;i<row*col;i++)
	    		//System.out.println(mybyte1[i]);
	    	
	    	Mat M=new Mat(mybyte1);
	    	M=M.reshape(1,row);
	    	//System.out.println(M.rows()+" "+M.cols());
	    	return M;
	    		
	    	
	    	
	       
    

 	    }
	    
	    /** 
	     *  
	     * ����˵��:StringתMat
	     
	     * 
	     */  

		    public static String MattoString(Mat mymat,double time)
		    {
		    	String Mystr=String.valueOf(mymat.rows())+" "+String.valueOf(mymat.cols())+" "+String.valueOf(time)+"\n";
		       

		      int size = (int) (mymat.total() * mymat.channels());
		      

		      for(int i=0;i<mymat.rows();i++)
		      {
		    	   if ((mymat.ptr(i,0)).get(1)>=0)
			   	        Mystr=Mystr+String.valueOf((mymat.ptr(i,0)).get(1));
			   	             	    	  else Mystr=Mystr+String.valueOf((mymat.ptr(i,0)).get(1)+256);
		   	      for(int j=1;j<mymat.cols();j++)
		   	      {

		   	          if ((mymat.ptr(i,j)).get(1)>=0)
		   	        Mystr=Mystr+" "+String.valueOf((mymat.ptr(i,j)).get(1));
		   	             	    	  else Mystr=Mystr+" "+String.valueOf((mymat.ptr(i,j)).get(1)+256);
		   	      }
		   	      Mystr=Mystr+"\n";
		      }
		     // System.out.println(Mystr);

	 	    	return Mystr; 
	 	    }
    /** 
     *  
     * ����˵��:����mat��ָ��·�� 
     * @param mat 
     * Ҫ�����Mat 
     * @param filePath  
     * ����·�� 
    
     * 
     */  
    public static boolean imWrite(Mat mat,String filePath){  
    //���������ģ�ֱ��ʹ��opencvԭ���������б���  
        if(!containChinese(filePath)){  
      return opencv_imgcodecs.imwrite(filePath, mat);  
        }  
       try {  
     /** 
      * ��matתΪjava��BufferedImage 
      */  
      ToMat convert= new ToMat();  
      Frame frame= convert.convert(mat);  
      Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();  
      BufferedImage bufferedImage= java2dFrameConverter.convert(frame);  
      ImageIO.write(bufferedImage, "PNG", new File(filePath));  
        
      return true;  
      } catch (Exception e) {  
    System.out.println("�����ļ������쳣:"+filePath);  
    e.printStackTrace();  
      }  
    return false;  
    }  
    /** 
       *  
       * ����˵��:�ж��ַ��Ƿ�������� 
       * @param inputString 
       * @return boolean 
       
       * 
       */  
    private static boolean containChinese(String inputString){  
          //�Ķη�Χ������ȫ��  
          String regex ="[\\u4E00-\\u9FA5\\u2E80-\\uA4CF\\uF900-\\uFAFF\\uFE30-\\uFE4F]";  
          Pattern pattern = Pattern.compile(regex);  
         Matcher matcher = pattern.matcher(inputString);  
         return matcher.find();  
      }  
}  