
public class Room {
    
    private int roomId;
    private String roomName;
    private String roomType;
    private String assignmentUserRole;
    private int assignmentUserId;

    public Room(int roomId, String roomName, String roomType, String assignmentUserRole, int assignmentUserId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.assignmentUserRole = assignmentUserRole;
        this.assignmentUserId = assignmentUserId;
    }

    public int returnRoomId() {
        return this.roomId;
    }

    public String returnRoomName() {
        return this.roomName;
    }

    public String returnRoomType() {
        return this.roomType;
    }

    public String returnAssignmentUserRole() {
        return this.assignmentUserRole;
    }

    public int returnAssignmentUserId() {
        return this.assignmentUserId;
    }
}
