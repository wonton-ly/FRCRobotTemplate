package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftMaster;
    public final XCANTalon leftFollower1;
    public final XCANTalon leftFollower2;
    public final XCANTalon rightMaster;
    public final XCANTalon rightFollower1;
    public final XCANTalon rightFollower2;
    int ticksPerInch = 217;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.leftMaster = factory.createCANTalon(33);
        this.leftFollower1 = factory.createCANTalon(34);
        this.leftFollower2 = factory.createCANTalon(32);

        this.rightMaster = factory.createCANTalon(22);
        this.rightFollower1 = factory.createCANTalon(21);
        this.rightFollower2 = factory.createCANTalon(23);

        leftMaster.configureAsMasterMotor(this.getName(), "leftMaster", false, false);
        rightMaster.configureAsMasterMotor(this.getName(), "rightMaster", true, true);

        leftFollower1.configureAsFollowerMotor(leftMaster, false);
        leftFollower2.configureAsFollowerMotor(leftMaster, false);

        rightFollower1.configureAsFollowerMotor(rightMaster, true);
        rightFollower2.configureAsFollowerMotor(rightMaster, true);
    }

    public void tankDrive(double leftPower, double rightPower) {
        this.leftMaster.simpleSet(leftPower);
        this.rightMaster.simpleSet(rightPower);
    }

    public double ticksToInches(double ticks){
        return ticks/ticksPerInch;
    }

    public double getLeftTotalDistance(){
        return -1*ticksToInches((leftMaster.getSelectedSensorPosition(0)));
    }

    public double getRightTotalDistance(){
        return ticksToInches(rightMaster.getSelectedSensorPosition(0));
    }
   
}
