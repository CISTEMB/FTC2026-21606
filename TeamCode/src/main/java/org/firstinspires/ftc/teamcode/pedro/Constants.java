package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.xPodOffset.set(0.1);
                c.yPodOffset.set(0.1);
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
            }
    );
    public static MecanumConfig drivetrainConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("front-left");
                c.backLeftName.set("back-left");
                c.frontRightName.set("front-right");
                c.backRightName.set("back-right");

                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);

                c.manualBrakeMode.set(true);
            }
    );
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(1.0);
                Controller secondaryTranslationalForward = Controller.proportional(1.0);
                Controller primaryTranslationalLateral = Controller.proportional(1.0);
                Controller secondaryTranslationalLateral = Controller.proportional(1.0);
                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(1.0, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(1.0, primaryTranslationalLateral));
                c.coast.set(Controller.proportionalFeedforward(1.0));
                c.brake.set(Controller.proportionalFeedforward(1.0));
                c.headingFeedback.set(Controller.proportional(1.0));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(1.0, 1.0));
                c.linearBrakeCoefficients.set(Matrix.diag(1.0, 1.0));
                c.quadraticBrakeCoefficients.set(Matrix.diag(1.0, 1.0));
                c.maxAchievableForwardVelocity.set(1.1);
                c.maxAchievableStrafeVelocity.set(1.0);
                c.naturalForwardDeceleration.set(0.1);
                c.naturalStrafeDeceleration.set(0.1);
            }
    );
    public static Follower create(HardwareMap hardwareMap) {
        return new Follower(
                new PinpointLocalizer(hardwareMap, localizerConfig),
                new Mecanum(hardwareMap, drivetrainConfig),
                new Foresight(foresightConfig)
             );
    }
}