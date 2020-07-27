import java.util.ArrayList;
import java.util.Comparator;

public class FastCollinearPoints{
	private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
	private ArrayList<Point> pointsCopy = new ArrayList<Point>();
	private Point[] sortedPoints;
	private boolean firstTime = true;
	private int index;
	private int size;
	// Finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		IllegalArgumentException e = new IllegalArgumentException();
		if(points == null) {
			throw e;
		}
		for(Point p : points) {
			if(p == null) {
				throw e;
			}
			sortedPoints = new Point[points.length - 1];
			int index = 0;
			for(Point q : points) {
				if(q == null) {
					throw e;
				}
				if(q.compareTo(p) != 0) {
					sortedPoints[index] = q;
					index++;
				} 
			}
			sort(p, sortedPoints);
		}
	}
	// The number of line segments
	public int numberOfSegments() {
		return lineSegments.size();
	}
	// The line segments
	public LineSegment[] segments() {
		LineSegment[] segmentsArray = new LineSegment[lineSegments.size()];
		for(int i=0; i<lineSegments.size(); i++) {
			segmentsArray[i] = lineSegments.get(i);
		}
		return segmentsArray;
	}
	
	private Point[] sort(Point p, Point[] points) {
		if(firstTime) {
			index = 0;
			size = points.length;
			firstTime = false;
		}
		
		if(pointsCopy.size()>1) {
			size = pointsCopy.size() / 2;
			for(int i=index; i<size; i++) {
				pointsCopy.add(i, points[i]);
			}
		}
		
		Comparator<Point> pointComparator = p.slopeOrder();
		int max = points.length;
		int mid = max/2; 
		int firstIndex = 0;
		int secondIndex = mid + 1;
		int compareNum = pointComparator.compare(points[firstIndex], points[secondIndex]);
		if(compareNum>0) {
			Point temp = points[firstIndex];
			points[firstIndex] = points[secondIndex];
			points[secondIndex] = temp;
		}
		if(compareNum==0) {
			secondIndex++;
		}
		return points;
	}
}
