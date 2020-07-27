import java.util.ArrayList;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
	
	// Finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		IllegalArgumentException e = new IllegalArgumentException();
		if(points == null) {
			throw e;
		}
		for(int i=0; i<points.length - 3; i++) {
			for(int j=i+1; j<points.length - 2; j++) {
				for(int k=j+1; k<points.length - 1; k++) {
					for(int l=k+1; l<points.length; l++) {
						if(points[i] == null || points[j] == null || points[k] == null || points[l] == null || points[i].compareTo(points[j]) == 0 || points[j].compareTo(points[k]) == 0 || points[k].compareTo(points[l]) == 0) {
							throw e;
						}
						if(points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {
							LineSegment segment = new LineSegment(points[i], points[k]);
							lineSegments.add(segment);
						}
					}
				}
			}
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
}
