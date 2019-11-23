package competition.subsystems.drive.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class ArcadeDriveCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    double y;
    double x;

    @Inject
    public ArcadeDriveCommand(OperatorInterface oi, DriveSubsystem driveSubsystem) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);

    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        y = oi.gamepad.getRightVector().y;
        x = oi.gamepad.getRightVector().x;
        double leftPower = y + x;
        double rightPower = y - x;
        driveSubsystem.tankDrive(leftPower, rightPower);

    }
}