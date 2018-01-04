package org.firstinspires.ftc.teamcode;

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
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp
 */
@TeleOp(name = "Driving Period")
public class RTeleOPREA extends OpMode {
     Servo jewel_vertical;
     DcMotor motor_right;
     DcMotor motor_left;
     DcMotor motor_backleft;
     DcMotor motor_backright;
     Servo servo_leftarm;
     Servo servo_rightarm;
     Servo Servo233;
     Servo turn_hand;
     Servo open_hand;
     DcMotor dcMotor_middle;

    int y = 10;
    int mx = 10;
    int b;
    @Override
    public void init() {
        dcMotor_middle = hardwareMap.dcMotor.get("dcMotor_middle");
        Servo233 = (Servo) hardwareMap.servo.get("servo23");
        turn_hand = hardwareMap.servo.get("turn_hand");
        open_hand = hardwareMap.servo.get("open_hand");
        jewel_vertical = hardwareMap.servo.get("jewel_vertical");
        servo_leftarm = hardwareMap.servo.get("left_glyph");
        servo_rightarm = hardwareMap.servo.get("right_glyph");
        motor_left = hardwareMap.dcMotor.get("left_drive");
        motor_backleft = hardwareMap.dcMotor.get("left_back_drive");
        motor_backright = hardwareMap.dcMotor.get("right_back_drive");
        motor_right = hardwareMap.dcMotor.get("right_drive");
      //  turn_arm = hardwareMap.crservo.get("turn_arm");
       // turn_hand = hardwareMap.servo.get("turn_hand");
       //open_hand = hardwareMap.servo.get("open_hand");

       motor_left.setDirection(DcMotorSimple.Direction.REVERSE);
       motor_backleft.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    @Override
    public void loop() {
        float x1 = -gamepad1.left_stick_x, y1 = gamepad1.left_stick_y;
        float x2 = gamepad1.right_stick_x;

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

        // Scale movement
        double max = Math.max(Math.abs(leftFrontPower), Math.max(Math.abs(leftBackPower),
                Math.max(Math.abs(rightFrontPower), Math.abs(rightBackPower))));

        if (max > 1) {
            leftFrontPower = (float) Range.scale(leftFrontPower, -max, max, -.5, .5);
            leftBackPower = (float) Range.scale(leftBackPower, -max, max, -.5, .5);
            rightFrontPower = (float) Range.scale(rightFrontPower, -max, max, -.5, .5);
            rightBackPower = (float) Range.scale(rightBackPower, -max, max, -.5, .5);
        }

        motor_backleft.setPower(-leftBackPower);
        motor_left.setPower(-leftFrontPower);
        motor_right.setPower(-rightFrontPower);
        motor_backright.setPower(-rightBackPower);

        // Here you set the motors' power to their respected power double.
        // start of controller movements

        if (gamepad2.dpad_up) {
            dcMotor_middle.setPower(1);
        } else {
            dcMotor_middle.setPower(0);
        }

        if (gamepad2.dpad_down) {
            dcMotor_middle.setPower(-1);
        } else {
            dcMotor_middle.setPower(0);
        }

       /* if(gamepad2.a) {
            servo_leftarm.setPosition(.55);
            servo_rightarm.setPosition(.47);
        }
        if(gamepad2.b){
            servo_rightarm.setPosition(.78);
            servo_leftarm.setPosition(.2);
*/
        if (gamepad2.a) {
            servo_leftarm.setPosition(.55);
            servo_rightarm.setPosition(.35);
        }
        if (gamepad2.b) {
            servo_rightarm.setPosition(.88);
            servo_leftarm.setPosition(.07);

        }
        if(gamepad2.left_bumper){
            Servo233.setPosition(.38);
        }else{
            Servo233.setPosition(1);
        }
        //correct positionng tested 12/15/2017
        if(gamepad2.right_bumper){
            turn_hand.setPosition(-1);
        }else{
            turn_hand.setPosition(.54);
        }
        //correct positioning tested 12/15/2017
        if (gamepad2.y){
            open_hand.setPosition(.54);
        }else{
            open_hand.setPosition(0.23);
        }
        telemetry.addData("servo23 data",Servo233.getPosition());
        telemetry.update();
        //correct positioning tested 12/16/2017
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