package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.ArchetypeHardware;

/**
 * Created by Shaun Sarker on 12/9/2017.
 */
//Follow the class structure and formatting
public class AutonomousDemo extends LinearOpMode{
    public ArchetypeHardware robot = new ArchetypeHardware();
    public void runOpMode () {
        robot.init(hardwareMap);
        waitForStart();
        robot.driveByTime(6,"LEFT",0.6);
    }
}
