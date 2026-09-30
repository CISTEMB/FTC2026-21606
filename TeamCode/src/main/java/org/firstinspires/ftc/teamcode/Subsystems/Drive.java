package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.FunctionalCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Libs.Commands;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import lombok.val;

public class Drive extends SubsystemBase {
    // Field Centric Constants

    //Hardware
    private final Follower follower;
    private Telemetry telemetry;
    public Follower getFollower(){
        return follower;
    }
    private double headingOffset_Rad;
    public void setHeadingOffset(double radians){
        headingOffset_Rad = radians;

    }
    public Drive(HardwareMap hw, Telemetry telemetry){
        follower = Constants.create(hardwareMap);
        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
    }



    public Command setForward() {
        return Commands.runOnce(() -> follower.setHeading(Math.toRadians(90))
        );}
//    public Command follow(PathChain pathChain) {
//        return new FollowPathCommand(this.getFollower(), pathChain).addRequirements(this);
//    }

    public Command driveWithGamepad
            (Gamepad gamepad) {
        double forward = -gamepad.left_stick_y;
        double strafe = -gamepad.left_stick_x;
        double turn = -gamepad.right_stick_x;


        forward *= Math.abs(forward);
        strafe *= Math.abs(strafe);
        turn *= Math.abs(turn);


        if (gamepad.right_trigger > 5) {
            forward *= 0.25;
            strafe *= 0.25;
            turn *= 0.15;
            telemetry.addData("Drive: SlowModeTrue", true);
        }
        else {
            telemetry.addData("Drive: SlowModeFalse", false);
        }
        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                strafe,
                turn,
                follower.pose().heading()

        );
        FunctionalCommand position = new FunctionalCommand(
                //execute
                () -> {

                    telemetry.addData("Position", follower.pose());
                    follower.manual(powers);
                },
                //end
                new Runnable() {
                    @Override
                    public void run() {
                        follower.manual(0, 0, 0);
                    }
                },
                (interrupted) -> follower.manual(0, 0, 0),
                () -> false,
                this

        );
        return position;

    }
}
