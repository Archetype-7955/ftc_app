package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.ArchetypeHardware;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
/**
 * Created by Shaun Sarker on 12/9/2017.
 */
//This is how TeleOP classes are written
@TeleOp(name = "Archetype TeleOP")
public class ArchetypeTeleOP extends OpMode{
    public ArchetypeHardware robot;
    //Tf why don't you just write the values
    double leftInit = 1.0;
    double rightInit = 0.0;
    double leftExp = 0.0;
    double rightExp = 1.0;
    public void init(){
        robot = new ArchetypeHardware();
        robot.init(hardwareMap);

    }

    public void loop(){
        //If it's completely inverted, take off the negative signs
        double z = -gamepad1.right_stick_y;
        double y = -gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        if (gamepad1.b) {
            robot.leftG.setPosition(leftInit);
            robot.rightG.setPosition(rightInit);
        }
        else if (gamepad1.y) {
            robot.leftG.setPosition(leftExp);
            robot.rightG.setPosition(rightExp);
        }
        else {
            //tf???????
            robot.gLift.setPower(gamepad2.left_stick_y);
        }
        robot.drive(x,y,z);


    }
}
