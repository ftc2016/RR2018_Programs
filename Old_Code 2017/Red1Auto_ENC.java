package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="Red1Auto_ENC")
public class Red1Auto_ENC extends LinearOpMode {
    public double circumference_of_mecanum_wheels = 12.56;

    VuforiaLocalizer vuforia;
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    private Servo leftGlyph;
    private Servo glyphRight;
    private Servo jewelVertical;
    private Servo jewelHorizontal;
    private ColorSensor jewelColor;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive = (DcMotor) hardwareMap.get("left_drive");
        rightDrive = (DcMotor) hardwareMap.get("right_drive");
        leftBackDrive = (DcMotor) hardwareMap.get("left_back_drive");
        rightBackDrive = (DcMotor) hardwareMap.get("right_back_drive");
        leftGlyph = (Servo) hardwareMap.get("left_glyph");
        glyphRight = (Servo) hardwareMap.get("right_glyph");
        jewelVertical = hardwareMap.get(Servo.class, "jewel_vertical");
        jewelHorizontal = hardwareMap.get(Servo.class, "jewel_horizontal");
        jewelColor = (ColorSensor) hardwareMap.get("jewel_color");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // our license key is below replace it if needed
        parameters.vuforiaLicenseKey = "Ac0dfNr/////AAAAGVIj/WGQ20ausA1vrtvVr/MVsIByyuYLEuY0rlXewrVHaCzLe1iRW9q6+nvnKQOcZk7Sg2eOfib/cpA7NbtqD7E6tD2FegRNKdqTLlVwNE4oT2/Sv60VBMyMAEUSMk8ZTXMZ/4alBqwUqFe2ajodtauM+Vf2SGL1/GPcaeCvEDwK0J7mr2ggfyLcLKFcky3oZCrYOlRGKGKLbOkAFOUbJrMxrbjbcKCrP9vH4F3Sf2ArJIJnij+WzVk7NcLe25Sml0rppRjHvMscSjfHvK1U36G02f6SimOWPBu3zekvAuqJ+kG5Tl3WvlsLZLGzv8R35ovQYra9cYrZzhf7CdmGEo6HhDXaQdt3mWzWby7L30Nn";
        // if you do replace it please upload new .svg files to the developer.vuforia.com
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");

        RelicRecoveryVuMark vuMark;

        //at this point everything is readu to go, the above happens when you press init and as soon as you press play everything starts

        waitForStart();
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        //When "Init" button is pressed
        jewelColor.enableLed(true);
        leftGlyph.setPosition(0.55);
        glyphRight.setPosition(0.47);

        while (opModeIsActive()) {
            relicTrackables.activate();
            jewelVertical.setDirection(Servo.Direction.FORWARD);
            jewelVertical.setPosition(.99);
            sleep(3000);

            if (jewelColor.red() > jewelColor.blue()) {
                telemetry.addData("Blue Color:", jewelColor.blue());
                telemetry.addData("Red Color:", jewelColor.red());
                telemetry.update();
                jewelHorizontal.setPosition(0.9);
                sleep(1000);
                jewelHorizontal.setPosition(0.5);
            } else {
                telemetry.addData("Red Color:", jewelColor.red());
                telemetry.addData("Blue Color:", jewelColor.blue());
                telemetry.update();
                jewelHorizontal.setPosition(0.1);
                sleep(1000);
                jewelHorizontal.setPosition(0.5);
            }
            sleep(1000);
            jewelVertical.setPosition(-0.5);

            int leftCount = 0;
            int centerCount = 0;
            int rightCount = 0;
            for (int i = 0; i < 20; i++) {
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                sleep(100);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    if (vuMark == RelicRecoveryVuMark.LEFT) {
                        telemetry.addData("Key: ", "Left");
                        telemetry.update();
                        leftCount++;
                    } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                        telemetry.addData("Key: ", "Middle");
                        telemetry.update();
                        centerCount++;
                    } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                        telemetry.addData("Key: ", "Right");
                        telemetry.update();
                        rightCount++;
                    }
                }
                vuMark = null;
            }
            relicTrackables.deactivate();
            relicTemplate = null;
            parameters = null;
            this.vuforia = null;


            // more variable checking
            //int distance = 2300;
            //   moveBackward(1400);

            double distance = 2.71;
            if (leftCount > centerCount && leftCount > rightCount) {
                distance = distance + 0.7;
                telemetry.addData("Position: ", "Left");
                telemetry.update();
            } else if (centerCount > leftCount && centerCount > rightCount) {
                distance = distance;
                telemetry.addData("Position: ", "Center");
                telemetry.update();
            } else if (rightCount > centerCount && rightCount > leftCount) {
                distance = distance - 0.7;
                telemetry.addData("Position: ", "Right");
                telemetry.update();

            }

            sleep(1000);

            //Main Code Goes Here

            moveBackward(distance);
            turnLeft(1.55);
            moveForward(1);
            //Open Arms
            leftGlyph.setPosition(-0.55);
            glyphRight.setPosition(-0.47);
            moveBackward(0.44);


            //Stop OPMode
            requestOpModeStop();
            break;
        }
    }

    public void moveForward(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts (distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() + (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition() + (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() + (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() + (COUNTS)));

        setPower(1);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }

    public void moveBackward(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts (distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() - (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition() - (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() - (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() - (COUNTS)));

        setPower(10);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }


    public void moveRight(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts (distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() - (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition() + (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() + (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() - (COUNTS)));

        setPower(1);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }


    public void moveLeft(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts (distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() + (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition()- (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() - (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() + (COUNTS)));

        setPower(1);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }
    public void turnRight(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts(distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() - (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition() + (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() - (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() + (COUNTS)));

        setPower(1);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }
    public void turnLeft (double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int COUNTS = distanceToCounts(distance);

        rightDrive.setTargetPosition((rightDrive.getCurrentPosition() + (COUNTS)));
        leftDrive.setTargetPosition((leftDrive.getCurrentPosition() - (COUNTS)));
        rightBackDrive.setTargetPosition((rightBackDrive.getCurrentPosition() + (COUNTS)));
        leftBackDrive.setTargetPosition((leftBackDrive.getCurrentPosition() - (COUNTS)));

        setPower(1);

        while (opModeIsActive() && leftBackDrive.isBusy() && rightBackDrive.isBusy() &&
                rightDrive.isBusy() && leftDrive.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        sleep(100);
    }


    public void runToPosition() {
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public int distanceToCounts(double rotations1){
        int rotations = (int) Math.round (rotations1 * 1120);
        return Math.round(rotations);
    }

    public void setPower(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);

    }

}
