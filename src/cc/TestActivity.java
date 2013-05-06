package cc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import cc.io._FileInputStream;
import cc.io._FileOutputStream;
import cc.test.R;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            // obviously does not exist so our exception will be caught.
            Method multiCatchTest = getClass().getDeclaredMethod("doesNotExist");
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException e) {
            Toast.makeText(this, "Multicatch exception was caught, yay",
                Toast.LENGTH_LONG).show();
        }

        /* Java 8 won't work...

        Comparator<Integer> cmp = (x, y) -> (x < y) ? -1 : ((x > y) ? 1 : 0);

        if (cmp.compare(1, 2) == -1) {
            Toast.makeText(this, "1 is less than 2", Toast.LENGTH_LONG).show();
        }*/

        String[] months = {
            "JAN",
            "FEB",
            "MAR",
            "APR",
            "MAY",
            "JUN",
            "JUL",
            "AUG",
            "SEP",
            "OCT",
            "NOV",
            "DEC"};

        int m = getRandomMonth(12);

        Toast.makeText(this,
            String.format(
                "%s has %d days in its month.",
                months[m],
                getDaysInMonth(months[m])),
            Toast.LENGTH_LONG).show();

        try (_FileOutputStream fos = new _FileOutputStream("/sdcard/testfile.txt")) {
            fos.write(10_000);
            fos.write(20_000_000);
            Toast.makeText(this, String.format("Written to testfile.txt: %d %d", 10_000, 20_000_000), Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            Toast.makeText(this, String.format("Error: %s", ioe.getMessage()),
                Toast.LENGTH_LONG).show();
        }

        try (_FileInputStream fis = new _FileInputStream("/sdcard/testfile.txt")) {
            Toast.makeText(
                this,
                String.format("Read data: %d %d %d %d",
                    fis.read(),
                    fis.read(),
                    fis.read(),
                    fis.read()),
                Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            Toast.makeText(this, String.format("Error: %s", ioe.getMessage()),
                Toast.LENGTH_LONG).show();
        }
    }

    public static int getDaysInMonth(String month)
    {
        switch (month) {
            case "JAN":
                return 31;
            case "FEB":
                return 28;
            case "MAR":
                return 31;
            case "APR":
                return 30;
            case "MAY":
                return 31;
            case "JUN":
                return 30;
            case "JUL":
            case "AUG":
                return 31;
            case "SEP":
                return 30;
            case "OCT":
                return 31;
            case "NOV":
                return 30;
            case "DEC":
                return 31;
            default:
                return 0;
        }
    }

    public static int getRandomMonth(int limit)
    {
        return (int) (Math.random() * limit);
    }
}
