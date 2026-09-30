package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.RepeatCommand;


import org.firstinspires.ftc.teamcode.Libs.CommandGamepad;
import org.firstinspires.ftc.teamcode.Libs.Commands;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;



@Configurable
public abstract class RobotBase extends CommandOpMode {

    protected boolean isRed = false;

    //Subsystems
    protected Drive drive;



    //Operator Interface
    protected CommandGamepad commandGamepad1;
    protected CommandGamepad commandGamepad2;

    //Helpers

    protected JoinedTelemetry joinedTelemetry;

    @Override
    public void initialize() {

        commandGamepad1 = new CommandGamepad(gamepad1);
        commandGamepad2 = new CommandGamepad(gamepad2);

        reset();
        joinedTelemetry = new JoinedTelemetry(
                PanelsTelemetry.INSTANCE.getFtcTelemetry(),
                telemetry);

        drive = new Drive(hardwareMap, joinedTelemetry);

        configureCommands();
    }

    protected abstract void configureCommands();

    @Override
    public void run() {
        super.run();
        joinedTelemetry.update();
    }


    public void setRedAlliance() {
        drive.setHeadingOffset(Math.toRadians(0));
        isRed = true;
    }

    public void setBlueAlliance() {
        drive.setHeadingOffset(Math.toRadians(180));
        isRed = false;

    }
    // Commands
}