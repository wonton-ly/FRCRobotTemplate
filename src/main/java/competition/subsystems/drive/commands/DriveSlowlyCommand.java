package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class DriveSlowlyCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;

    @Inject
    public DriveSlowlyCommand(OperatorInterface oi, DriveSubsystem driveSubsystem) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);

        this.setTimeout(2);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        driveSubsystem.tankDrive(
            .15, .15);

    }

    @Override
    public boolean isFinished() {
        return this.isTimedOut();
    }

}