package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;


public class PosToNegCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    double power;
    boolean max;

    @Inject
    public PosToNegCommand(OperatorInterface oi, DriveSubsystem driveSubsystem) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);

    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        if (max == false) {
            power += 0.1;
            if (Math.abs(1.0 - power) <= .01) {
                max = true;
            }
        }
        if (max) {
            power -= 0.1;
            if (power <= .01) {
                max = false;
            }
            if(power < 0){
                power = 0;
            }
        }

        driveSubsystem.tankDrive(
            power, power);

    }

}