package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;


public abstract class TeleOp extends RobotBase {
    public static Pose startPose;

   @com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Blue")
    public static class TeleOpBlue extends TeleOp {
        @Override
        public void initialize() {
            super.initialize();
            setBlueAlliance();
            if (startPose == null) {
                drive.getFollower().setPose(new Pose(0,0));
            } else drive.getFollower().setPose(startPose);
        }
    }
   @com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Red")
    public static class TeleOpRed extends TeleOp {
        @Override
        public void initialize() {
            super.initialize();
            setRedAlliance();
            if (startPose == null) drive.getFollower().setPose(new Pose(0,0));
            else drive.getFollower().setPose(startPose);
        }
    }
    @Override
    protected void configureCommands() {

        // Defaults


        drive.setDefaultCommand(drive.driveWithGamepad(gamepad1));


        // Gamepad 1

        commandGamepad1.back().whenPressed(drive.setForward());

        // Gamepad 2

    }
}