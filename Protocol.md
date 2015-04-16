# Packet Protocol #
Documentation on the protocol used between the program in java-remote-administration-tool

All of the protocol's data types are the java standard. See the [java documentation](http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html) for more information

## Command Packet's ##

| **Packet ID** | **Purpose** | **Field Description** | **Field Type** |  **Notes** |
|:--------------|:------------|:----------------------|:---------------|:-----------|
| 1 | Move mouse | {Packet ID, X position, Y position} | {byte,int,int} | Used to move mouse cursor to coordinate (X,Y) |
| 2 | Click mouse | {Packet ID, Mouse key number , State} | {byte, byte, byte } | Used for generating mouse clicks. Key state: 0 = key released, 1 = key pressed down |
| 3 | Key event | {Packet ID, Key number, State} | {byte,int,byte | The key with the specified number is: released if state = 0, pressed if state = 1 |

## Images Packet's ##

| **Packet ID** | **Purpose** | **Field Description** | **Field Type** |  **Notes** |
|:--------------|:------------|:----------------------|:---------------|:-----------|
| 4 | Sends an image | {Packet ID, Image} | {byte, byte`[]`} | The image is converted to jpeg using ImageIO before sent over socket |

### JPEG converting ###

The code examples is not fully working code, just a summary with the important parts for the image converting. For the fully working code see [ImageThreadSender.java](http://code.google.com/p/java-remote-administration-tool/source/browse/src/slave/remoteControl/ImageThreadSender.java) in slave package and [ImageThread.java](http://code.google.com/p/java-remote-administration-tool/source/browse/src/master/remoteControl/ImageThread.java) in master package

#### Encode ####
```
ByteArrayOutputStream baos = new ByteArrayOutputStream();
//Where getScreen() takes a print screen and return it in a BufferedImage
ImageIO.write(this.getScreen(), "jpg", baos);
baos.flush();
//Send the byte array
baos.toByteArray();
```

#### Decode ####
```
byte[] imageByte;
//Now fill the imageByte array with the image
...
//Start converting the image
InputStream inImage = new ByteArrayInputStream(imageByte);
BufferedImage bi = ImageIO.read(inImage);
//Create and ImageIcon from the BufferedImage and display it.
```