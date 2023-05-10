package simple.test12345.Util;

public class OtpGen {
    public static String otpCode(){
        // Generate OTP
        double otp;
        otp = Math.random();
        otp = otp * 1000000;
        int token = (int) otp;
        System.out.println(token);

        return String.format("%06d",token);
    }
}
