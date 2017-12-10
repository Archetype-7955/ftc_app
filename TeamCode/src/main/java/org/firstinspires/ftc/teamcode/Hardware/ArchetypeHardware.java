package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
* This is the hardware class, it should be initialized at every Opmode
* This is about 16x easier than declaring all your motors and servos at the beginning of a class
*/
public class ArchetypeHardware {
    public DcMotor frontLeft,frontRight,backLeft,backRight,gLift;
    public Servo leftG,rightG;
    public double speedmultiplier;
    public static final int MOTOR_TICKS = 1120;// Andymark Motor Encoder
    public static final double mmWheelDiameter = 101.6;// For figuring circumference
    public ElapsedTime runtime = new ElapsedTime();
    public void init(HardwareMap hwMap) {
        speedmultiplier = 1.0;
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backLeft = hwMap.get(DcMotor.class, "rearLeft");
        backRight = hwMap.get(DcMotor.class, "rearRight");
        gLift = hwMap.get(DcMotor.class, "gLift");
        leftG = hwMap.get(Servo.class, "leftG");
        rightG = hwMap.get(Servo.class, "rightG");
        setDefaultDirection();
        setZeroPowerBehavior();
        stop();
    }
    //This Drive Function is better, because if you hit a dead zone, it throws an exception
    //However this Drive is Dual Stick
    public void drive(double x, double y, double z) {
        frontLeft.setPower(scalePower(speedmultiplier*(y-x)));
        backLeft.setPower(scalePower(speedmultiplier*(y+x)));
        frontRight.setPower(scalePower(speedmultiplier*(z+x)));
        backRight.setPower(scalePower(speedmultiplier*(z-x)));
    }

    public void OGdrive(double x, double y, double z) {
        double frontLeftY = y;
        double frontRightY = y;
        double backLeftY = y;
        double backRightY = y;

        //left-right motion
        double frontLeftX = x;
        double frontRightX = -1*x;
        double backLeftX = -1*x;
        double backRightX = x;

        //turning
        double frontLeftT = z;
        double frontRightT = -1*z;
        double backLeftT = z;
        double backRightT = -1*z;

        //summing the vectors
        double frontLeftTotal = frontLeftY + frontLeftX + frontLeftT;
        double frontRightTotal = frontRightY + frontRightX + frontRightT;
        double backLeftTotal = backLeftY + backLeftX + backLeftT;
        double backRightTotal = backRightY + backRightX + backRightT;

        frontLeft.setPower(scalePower(frontLeftTotal));
        frontRight.setPower(scalePower(frontRightTotal));
        backLeft.setPower(scalePower(backLeftTotal));
        backRight.setPower(scalePower(backRightTotal));
    }
    public void setMotorPower(double power){
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }

    public void setDefaultDirection(){
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        gLift.setDirection(DcMotor.Direction.REVERSE);
    }

    public void stop(){
        setMotorPower(0.0);
    }

    public double scalePower(double power) {
        //This Is The PowerScaling
        float scaledPower;
        power = Range.clip(power, -1, 1);
        float[] possiblePowerValues = {
                0.00f, 0.05f, 0.09f, 0.10f, 0.12f,
                0.15f, 0.18f, 0.24f, 0.30f, 0.36f,
                0.43f, 0.50f, 0.60f, 0.72f, 0.85f,
                1.00f, 1.00f
        };
        int powerIndex = (int)(power * 16.0);
        if (powerIndex < 0) {
            powerIndex = -powerIndex;
        } else if (powerIndex > 16) {
            powerIndex = 16;
        }
        if (power < 0) {
            scaledPower = -possiblePowerValues[powerIndex];
        } else {
            scaledPower = possiblePowerValues[powerIndex];
        }
        return scaledPower;
    }

    public void setZeroPowerBehavior(){
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveByTime(int seconds, String direction, double speed){
        runtime.reset();
        double x = 0.0, y = 0.0, z = 0.0;
        speedmultiplier = speed;
        if(direction.equals("FORWARD")){
            y = 1.0;
            z = 1.0;
        } else if(direction.equals("BACKWARDS")){
            y = -1.0;
            z = -1.0;
        } else if(direction.equals("LEFT") || direction.equals("PORTSIDE")){
            x = -1.0;
        } else if(direction.equals("RIGHT") || direction.equals("STARBOARD")){
            x = 1.0;
        } else {
            stop();
        }
        while( runtime.seconds()< seconds){
            drive(x,y,z);
        }
    }

}
