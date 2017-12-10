package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.ArchetypeHardware;

/**
 * Created by Shaun Sarker on 12/9/2017.
 */

@TeleOp(name = "Archetype OGTeleOP")
public class ArchetypeOGTeleOP extends OpMode{
    public ArchetypeHardware robot;
    //Tf why don't you just write the values
    //r make an initialization method
    double leftInit = 1.0;
    double rightInit = 0.0;
    double leftExp = 0.0;
    double rightExp = 1.0;
    public void init(){
        robot = new ArchetypeHardware();
        robot.init(hardwareMap);

    }

    public void loop(){
        //This is what your Original Single Stick Drive TeleOp Would Look Like If It Was Written To Standard
        if (gamepad1.b) {
            robot.leftG.setPosition(leftInit);
            robot.rightG.setPosition(rightInit);
        }
        else if (gamepad1.y) {
            robot.leftG.setPosition(leftExp);
            robot.rightG.setPosition(rightExp);
        }
        //Removed the Else Because It Is Redundant
        robot.gLift.setPower(gamepad2.left_stick_y);


        robot.OGdrive(-gamepad1.left_stick_x , gamepad1.left_stick_y, -gamepad1.right_stick_x);
    }
}