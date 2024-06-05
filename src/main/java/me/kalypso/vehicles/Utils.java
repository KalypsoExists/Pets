package me.kalypso.vehicles;

import org.joml.Quaternionf;

import java.io.File;

public class Utils {

    /*public static float[] stringToFloatArray(String[] array) {
        float[] floatArray = new float[array.length];

        try {
            int i = 0;
            for (String f : array) {
                floatArray[i++] = Float.parseFloat(f);
            }
        } catch (Exception ex) {
            return null;
        }

        return floatArray;
    }*/

    public static Quaternionf fromEulerAngles(double x, double y, double z) {

        x = Math.toRadians(x);
        y = Math.toRadians(y);
        z = Math.toRadians(z);

        double cr = Math.cos(x * 0.5);
        double sr = Math.sin(x * 0.5);
        double cp = Math.cos(y * 0.5);
        double sp = Math.sin(y * 0.5);
        double cy = Math.cos(z * 0.5);
        double sy = Math.sin(z * 0.5);

        double q0 = cr * cp * cy + sr * sp * sy;
        double q1 = sr * cp * cy - cr * sp * sy;
        double q2 = cr * sp * cy + sr * cp * sy;
        double q3 = cr * cp * sy - sr * sp * cy;

        return new Quaternionf(q1, q2, q3, q0);

    }

    public static String getExtension(File file) {
        String fn = file.toString();
        int lt = fn.lastIndexOf('.');
        if (lt > 0 && lt < fn.length() - 1) {
            return fn.substring(lt + 1);
        }
        return "";
    }

}
