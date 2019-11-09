package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class TankDriveWithJoysticksCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    DoubleProperty leftPowerPropInit;
    DoubleProperty rightPowerPropInit;
    DoubleProperty leftPowerPropFinal;
    DoubleProperty rightPowerPropFinal;

    @Inject
    public TankDriveWithJoysticksCommand(OperatorInterface oi, DriveSubsystem driveSubsystem, PropertyFactory pf) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);
        pf.setPrefix(this);
        leftPowerPropInit = pf.createEphemeralProperty("leftJoystickYInit", 0);
        rightPowerPropInit = pf.createEphemeralProperty("rightJoystickYInit", 0);

        leftPowerPropFinal = pf.createEphemeralProperty("leftJoystickYFinal", 0);
        rightPowerPropFinal = pf.createEphemeralProperty("rightJoystickYFinal", 0);
        
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double leftPower = oi.gamepad.getLeftVector().y;
        leftPowerPropInit.set(leftPower);
        double rightPower = oi.gamepad.getRightVector().y;
        rightPowerPropInit.set(rightPower);

        if(Math.abs(leftPower) < 0.1){  
            leftPower = 0;
        }
        else if(leftPower > 0){
            leftPower = (1/.9) * (leftPower - 0.11);
        }
        else if(leftPower < 0){
            leftPower = (1/.9)*leftPower + 0.11;
        }

        if(Math.abs(rightPower) < 0.1){
            rightPower = 0;
        }
        if(rightPower > 0){
            rightPower = (1/.9)*rightPower - 0.11;
        }
        else if(rightPower < 0){
            rightPower = (1/.9)*rightPower + 0.11;
        }

        leftPowerPropFinal.set(leftPower);
        rightPowerPropFinal.set(rightPower);

        driveSubsystem.tankDrive(
            leftPower, rightPower);

    }

}
