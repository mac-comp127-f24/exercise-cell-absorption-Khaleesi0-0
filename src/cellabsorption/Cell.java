package cellabsorption;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

import java.awt.Color;

public class Cell {
    private Ellipse shape;
    private double radius;
    private double direction;

    public Cell(double x, double y, double radius, Color color) {
        shape = new Ellipse(x, y, radius * 2, radius * 2);
        shape.setFillColor(color);
        this.radius = radius;
        this.direction = Math.random() * Math.PI * 2;
    }

    public Ellipse getShape() {
        return shape;
    }

    public Point getCenter() {
        return shape.getCenter();
    }

    public void moveAround(Point centerOfGravity, double wiggleness, double wanderFromCenter) {
        shape.moveBy(Math.cos(direction), Math.sin(direction));

        double distToCenter = shape.getCenter().distance(centerOfGravity);
        double angleToCenter = centerOfGravity.subtract(shape.getCenter()).angle();
        double turnTowardCenter = normalizeRadians(angleToCenter - direction);

        direction = normalizeRadians(
            direction
                + (Math.random() - 0.5) * wiggleness
                + turnTowardCenter * Math.tanh(distToCenter / wanderFromCenter));
    }

    public void grow(double amount) {
        setRadius(radius + amount);
    }

    public void setRadius(double newRadius) {
        if (newRadius < 0) {
            newRadius = 0;
        }
        radius = newRadius;
        Point previousCenter = shape.getCenter();
        shape.setSize(newRadius * 2, newRadius * 2);
        shape.setCenter(previousCenter);
    }

    private static double normalizeRadians(double theta) {
        double pi2 = Math.PI * 2;
        return ((theta + Math.PI) % pi2 + pi2) % pi2 - Math.PI;
    }
}
