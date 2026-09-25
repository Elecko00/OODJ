
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;


public class ModifyRooms {

    private String filePath = "data/hospital_room.txt";

    public String writeRoom(int selectedRoomId, String roomName, String roomType, String assignmentUserRole, int assignmentUserId, String action) {
        try {
            LoadRooms lr = new LoadRooms();
            lr.readRooms();
            List<Room> roomList = lr.returnRooms();
            if (action.equals("add")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath, true));
                bw.write(selectedRoomId + ", " + roomName + ", " + roomType + ", " + assignmentUserRole + ", " + assignmentUserId);
                bw.newLine();
                bw.close();
            } else if (action.equals("update")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath, false));
                for (Room a : roomList) {
                    if (a.returnRoomId() == selectedRoomId) {
                        bw.write(selectedRoomId + ", " + roomName + ", " + roomType + ", " + assignmentUserRole + ", " + assignmentUserId);
                        bw.newLine();
                    } else {
                        bw.write(a.returnRoomId() + ", " + a.returnRoomName() + ", " + a.returnRoomType() + ", " + a.returnAssignmentUserRole() + ", " + a.returnAssignmentUserId());
                        bw.newLine();
                    }
                }
                bw.close();
            } else if (action.equals("delete")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath, false));
                for (Room a : roomList) {
                    if (a.returnRoomId() != selectedRoomId) {
                        bw.write(a.returnRoomId() + ", " + a.returnRoomName() + ", " + a.returnRoomType() + ", " + a.returnAssignmentUserRole() + ", " + a.returnAssignmentUserId());
                        bw.newLine();
                    }
                }
                bw.close();
            } else {
                return "Invalid action";
            }     
        } catch (IOException e) {
            return "File not found";
        }
        return "Room update successfully."; 
    }

    public int returnLatestRoomId() {

        int maxRoomId = 0;
        LoadRooms lr = new LoadRooms();
        lr.readRooms();
        List<Room> roomList = lr.returnRooms();
        for (Room a : roomList) {
            if (a.returnRoomId() > maxRoomId) {
                maxRoomId = a.returnRoomId();
            }
        }
        return maxRoomId;
    }
}
