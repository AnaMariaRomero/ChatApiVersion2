package net.apimessages.pd2.management;


import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;


public class DetectorLanguage {

	/**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            DetectorFactory.loadProfile(Thread.currentThread().getContextClassLoader().getResource("lang").getPath());
        } catch (LangDetectException e)
        {
            e.printStackTrace();
        }

        Detector detect;
        try
        {
            detect = DetectorFactory.create();
            detect.append ("Conf�o en un confiar en un");
            System.out.println(detect.detect());
        } catch (LangDetectException e)
        {
            e.printStackTrace();
        }

    }
}

