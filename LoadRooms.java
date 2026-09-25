
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadRooms {

    private String filePath = "data/hospital_room.txt";
    private List<Room> roomList = new ArrayList<>();

    public String readRooms() {
        this.roomList.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                int roomId =  Integer.parseInt(data[0].trim());
                String roomName = data[1].trim();
                String roomType = data[2].trim();
                String assignmentUserRole = data[3].trim();
                int assignmentUserId = Integer.parseInt(data[4].trim());
                Room newRoomList = new Room(roomId, roomName, roomType, assignmentUserRole, assignmentUserId);
                this.roomList.add(newRoomList);
            }
            br.close();
        } catch (IOException e) {
            return "File not found";
        }
        return "Room successfully loaded";
    }

    public List<Room> returnRooms() {
        return new ArrayList<>(this.roomList);
    }
    
}