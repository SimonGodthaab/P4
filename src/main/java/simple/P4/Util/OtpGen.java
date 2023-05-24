package simple.P4.Util;

public class OtpGen {
    public static String otpCode() {
        // Generate OTP
        double otp;
        otp = Math.random();
        otp = otp * 1000000;
        int token = (int) otp;

        return String.format("%06d", token);
    }
}
