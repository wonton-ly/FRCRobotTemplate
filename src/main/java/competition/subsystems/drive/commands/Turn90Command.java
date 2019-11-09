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

public class Turn90Command extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi; 
    PIDManager pid;
    HeadingModule headingModule;
    PoseSubsystem pose;
    double goal;

    @Inject
    public Turn90Command(OperatorInterface oi, DriveSubsystem driveSubsystem, CommonLibFactory clf, PIDFactory pf, PoseSubsystem pose) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);
        this.pid = pf.createPIDManager("rotate");
        headingModule = clf.createHeadingModule(pid);
        this.pose = pose;

        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(3);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);

        pid.setP(0.04);
        pid.setD(0.2);

    }

    @Override
    public void initialize() {
        goal = pose.getCurrentHeading().getValue() + 90;

    }

    @Override
    public void execute() {
        double power = headingModule.calculateHeadingPower(goal);
        driveSubsystem.tankDrive(-power, power);
    }

    @Override
    public boolean isFinished() {
        return pid.isOnTarget();
    }
}