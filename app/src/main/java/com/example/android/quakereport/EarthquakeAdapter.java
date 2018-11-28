package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private List<Earthquake> earthquakes = new ArrayList<Earthquake>();

    public EarthquakeAdapter (Context context, List<Earthquake> earthquakeList) {
        // Initialise the ArrayAdapter's internal storage for the context and the list.
        // The second argument is only used when dealing with one TextView, but in this custom
        // adaptor we are dealing with 3 TextViews so the argument is not required and is set at 0
        super(context, 0, earthquakeList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is a spare view to reuse, and if not then inflate a new one
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the earthquake object in the given list as defined in the method parameter
        Earthquake currentEarthquake = getItem(position);

        // Find the desired TextView from the xml file, and set the text in this view to the
        // earthquake magnitude from the Earthquake class. The number of decimal places is
        // defined in the helper method
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Set the proper background colour of the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable;
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background colour based on the current earthquake magnitude
        int magnitudeColour = getMagnitudeColour(currentEarthquake.getMagnitude());

        // Set the colour on the magnitude circle
        magnitudeCircle.setColor(magnitudeColour);

        // Find the desired TextView from the xml file, and set the text in this view to the
        // earthquake primary location from the Earthquake class
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_text_view);
        // From the Earthquake object location, get the primary location (i.e. Montreal, Canada)
        // and set it to the TextView
        primaryLocationTextView.setText(primaryLocation(currentEarthquake));

        // Find the desired TextView from the xml file, and set the text in this view to the
        // earthquake offset location from the Earthquake class
        TextView offsetLocationTextView = (TextView) listItemView.findViewById(R.id.offset_location_text_view);
        // From the Earthquake object location, get the offset location (i.e. 75 km NE of)
        // and set it to the TextView
        offsetLocationTextView.setText(offsetLocation(currentEarthquake));

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliSeconds());

        // Find the desired TextView from the xml file, and set the text in this view to the
        // earthquake date from the Earthquake class
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Format the date string (i.e. "Mar 3, 1984") - use the helper method
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(formattedDate);

        // Find the desired TextView from the xml file, and set the text in this view to the
        // earthquake time from the Earthquake class
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        // Format the time string (i.e. "4:30 PM") - use the helper method
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    // Helper method to return the objects location
    private String primaryLocation(Earthquake currentEarthquake) {
        String location = currentEarthquake.getLocation();
        if (location.contains("of ")) {
            int splitIndex = location.indexOf("of ");
            return location.substring(splitIndex + 3, location.length());
        }
        return location;
    }

    // Helper method to return the objects offset location
    private String offsetLocation(Earthquake earthquake) {
        String location = earthquake.getLocation();
        if (location.contains("of ")) {
            int splitIndex = location.indexOf("of ");
            return location.substring(0, splitIndex + 3);
        }
        return "Near the ";
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColour(double magnitude) {
        int magnitudeColourResourceId;
        int magnitudeRoundedDown = (int)Math.floor(magnitude);
        switch (magnitudeRoundedDown) {
            case 0:
            case 1:
                magnitudeColourResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColourResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColourResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColourResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColourResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColourResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColourResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColourResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColourResourceId = R.color.magnitude9;
                break;
                default:
                    magnitudeColourResourceId = R.color.magnitude10plus;
                    break;
        }
        // The R.colour.magnitude9 just points to the resource we want in the colors.xml file
        // In order to get the actual integer colour value, use the call below
        return ContextCompat.getColor(getContext(), magnitudeColourResourceId);
    }
}

