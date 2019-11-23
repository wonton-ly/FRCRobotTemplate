package competition.subsystems.drive.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class DriveToPositionCommand extends BaseCommand{
    DriveSubsystem driveSubsystem;
    OperatorInterface oi;
    PIDManager pid;
    PoseSubsystem pose;
    HeadingModule headingModule;
    double goal;
    double currentPosition;

    @Inject
    public DriveToPositionCommand(OperatorInterface oi, DriveSubsystem driveSubsystem, CommonLibFactory clf, 
    PIDFactory pf, PoseSubsystem pose){
        this.driveSubsystem = driveSubsystem;
        this.oi = oi;
        this.requires(this.driveSubsystem);
        this.pid = pf.createPIDManager("DriveToPosition");
        headingModule = clf.createHeadingModule(pid);
        this.pose = pose;

        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(3);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);

        pid.setP(.5);
        pid.setD(4);
    }

    @Override
    public void initialize() {
        goal = driveSubsystem.getLeftTotalDistance() + 60;
    }

    @Override
    public void execute(){
        currentPosition = driveSubsystem.getLeftTotalDistance();
        double power = pid.calculate(goal, currentPosition);
        power *= .2;
        driveSubsystem.tankDrive(power, power);
    }

    @Override
    public boolean isFinished(){
        return pid.isOnTarget();
    }

}
