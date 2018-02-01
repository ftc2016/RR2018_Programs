package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.configuration.ServoConfiguration;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.os.SystemClock.sleep;


/**
 * TeleOp
 */
@TeleOp(name = "Driving Period")
public class RTeleOPREA extends OpMode {
     Servo jewel_vertical;
     DcMotor rightDrive;
     DcMotor leftDrive;
     DcMotor leftBackDrive;
     DcMotor rightBackDrive;
    Servo servo_leftarmbottom;
    Servo servo_leftarmtop;
    Servo servo_rightarmbottom;
    Servo servo_rightarmtop;
    DcMotor RelicExtend;
     Servo turn_hand;
     Servo open_hand;
     DcMotor dcMotor_middle;
    float extend = 6;
    int ralic_move_distance = 0;
    // above initializes all the aspects we need to make our robot function
    @Override
    public void init() {
        dcMotor_middle = hardwareMap.dcMotor.get("dcMotor_middle");
        RelicExtend = hardwareMap.dcMotor.get("extend_hand");
        turn_hand = hardwareMap.servo.get("turn_hand");
        open_hand = hardwareMap.servo.get("open_hand");
        jewel_vertical = hardwareMap.servo.get("jewel_vertical");
        servo_leftarmbottom = hardwareMap.servo.get("left_glyph_bottom");
        servo_leftarmtop = hardwareMap.servo.get("left_glyph_top");
        servo_rightarmbottom = hardwareMap.servo.get("right_glyph_bottom");
        servo_rightarmtop = hardwareMap.servo.get("right_glyph_top");
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        leftBackDrive = hardwareMap.dcMotor.get("left_back_drive");
        rightBackDrive = hardwareMap.dcMotor.get("right_back_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");
        // defining all the hardware
       leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
       leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
       //this puts the motors in reverse
        RelicExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    @Override
    public void loop() {
        float x1 = -gamepad1.left_stick_x;
        float y1 = gamepad1.left_stick_y;
        float x2 = gamepad1.right_stick_x;
        extend = 6;
        // Reset variables
        float leftFrontPower = 0;
        float leftBackPower = 0;
        float rightFrontPower = 0;
        float rightBackPower = 0;

        // Handle regular movement
        leftFrontPower += y1;
        leftBackPower += y1;
        rightFrontPower += y1;
        rightBackPower += y1;

        // Handle strafing movement
        leftFrontPower += x1;
        leftBackPower -= x1;
        rightFrontPower -= x1;
        rightBackPower += x1;

        // Handle turning movement
        leftFrontPower -= x2;
        leftBackPower -= x2;
        rightFrontPower += x2;
        rightBackPower += x2;
        jewel_vertical.setPosition(0);

        // Scale movement
        double max = Math.max(Math.abs(leftFrontPower), Math.max(Math.abs(leftBackPower),
                Math.max(Math.abs(rightFrontPower), Math.abs(rightBackPower))));

        if (max > 1) {
            leftFrontPower = (float) Range.scale(leftFrontPower, -max, max, -.5, .5);
            leftBackPower = (float) Range.scale(leftBackPower, -max, max, -.5, .5);
            rightFrontPower = (float) Range.scale(rightFrontPower, -max, max, -.5, .5);
            rightBackPower = (float) Range.scale(rightBackPower, -max, max, -.5, .5);
        }

        leftBackDrive.setPower(-leftBackPower);
        leftDrive.setPower(-leftFrontPower);
        rightDrive.setPower(-rightFrontPower);
        rightBackDrive.setPower(-rightBackPower);

        // Here you set the motors' power to their respected power double.
        // start of controller movements

        //press to raise linear lift
        if (gamepad2.dpad_up) {
            dcMotor_middle.setPower(1);
        } else {
            dcMotor_middle.setPower(0);
        }
        //press to lower linear lift
        if (gamepad2.dpad_down) {
            dcMotor_middle.setPower(-1);
        } else {
            dcMotor_middle.setPower(0);
        }

        if (gamepad2.a) {
            //grab glyphs
            servo_rightarmbottom.setPosition(0.9);
            servo_rightarmtop.setPosition(0.45);
            servo_leftarmbottom.setPosition(0.43);
            servo_leftarmtop.setPosition(0.35);
        }
        if (gamepad2.b) {
            //release glyphs
            servo_rightarmbottom.setPosition(0.42);
            servo_rightarmtop.setPosition(0);
            servo_leftarmbottom.setPosition(0.9);
            servo_leftarmtop.setPosition(0.8);
        }

        if (gamepad2.x) {
            //release glyphs
            servo_rightarmbottom.setPosition(0.71);
            servo_rightarmtop.setPosition(0.26);
            servo_leftarmbottom.setPosition(0.62);
            servo_leftarmtop.setPosition(0.54);
        }
        //hold down to extend relic arm
  /*      double RelicScaleLeft = 0;
        RelicScaleLeft -= -gamepad2.left_stick_x;
        RelicScaleLeft = (float) Range.scale(RelicScaleLeft, -max, max, 0.3, .6);
        RelicExtend.setPosition(RelicScaleLeft);

        double RelicScaleRight = 0;
        RelicScaleRight -= gamepad2.left_stick_x;
        RelicScaleRight = (float) Range.scale(RelicScaleRight, -max, max,0.6,0.9);
        RelicExtend.setPosition(RelicScaleRight);
        */

            //if(gamepad2.dpad_left){
              //  RelicExtend.setPosition(.97);
            //else{
               // RelicExtend.setPosition(.46);


        float gamepad2x1 = gamepad2.left_trigger;

        if(gamepad2x1>0.1 && ralic_move_distance < 6){
            //RelicExtend.setDirection(DcMotor.Direction.FORWARD);
            RelicMove(1);
            ralic_move_distance++;
        }


        float gamepad2y1 = gamepad2.right_trigger;

        if(gamepad2y1>0.1 && ralic_move_distance > 0){
            //RelicExtend.setDirection(DcMotor.Direction.REVERSE);
            RelicMove(-1);
            ralic_move_distance--;
            if(ralic_move_distance == 2){
                ralic_move_distance = 0;
            }
        }

        RelicMove(0);

        //hold down to raise the relic arm
        if(gamepad2.y){
            turn_hand.setPosition( 0.67);
        }else{
            turn_hand.setPosition(0.24);
        }
        //hold down to grab relic
        if (gamepad2.right_bumper){
            open_hand.setPosition(0.25);
        }else{
            open_hand.setPosition(0.65);
        }
        telemetry.update();
    }

    public void RelicMove(int distance){
        RelicExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int counts = distanceToCounts(distance);
        RelicExtend.setTargetPosition((RelicExtend.getCurrentPosition() - (counts)));
        setPower(.8);

        while (RelicExtend.isBusy()) {
            telemetry.addData("Running", "Encoders");
            telemetry.update();
        }
        setPower(0);
        //sleep(100);
    }

    public int distanceToCounts(double rotations1){
        int rotations = (int) Math.round (rotations1 * 510);
        return Math.round(rotations);
    }
    public void setPower(double power) {
        RelicExtend.setPower(power);
    }
        // end of loop



    @Override
    public void stop() {
    }
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.04, 0.08, 0.9, 0.11, 0.14, 0.17, 0.23, 0.29, 0.35, 0.42, 0.49, 0.59, 0.71, 0.84, 0.99, 1.00};
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        }
        if (index > 16) {
            index = 16;
        }
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }
        return dScale;
    }
}