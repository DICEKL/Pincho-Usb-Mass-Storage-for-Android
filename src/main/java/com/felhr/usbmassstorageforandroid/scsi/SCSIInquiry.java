package com.felhr.usbmassstorageforandroid.scsi;

import java.nio.ByteBuffer;

/**
 * Created by Felipe Herranz(felhr85@gmail.com) on 12/12/14.
 */
public class SCSIInquiry extends SCSICommand
{
    public static final byte INQUIRY_OPERATION_CODE = 0x12;

    private byte operationCode;
    private boolean evpd;
    private int pageCode;
    private int allocationLength;
    private byte control;


    public SCSIInquiry(boolean evpd, int pageCode, int allocationLength)
    {
        this.dataTransportPhase = true;
        this.direction = 1;
        this.operationCode = INQUIRY_OPERATION_CODE;
        this.evpd = evpd;
        this.pageCode = pageCode;
        this.allocationLength = allocationLength;
        this.control = 0x00;
    }

    @Override
    public byte[] getSCSICommandBuffer()
    {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.put(operationCode);
        if(evpd)
            buffer.put((byte) 0x00);
        else
            buffer.put((byte) 0x01);
        buffer.put(convertToByte(pageCode, 1));
        buffer.put((byte) 0x00); // RESERVED byte
        buffer.put(convertToByte(allocationLength, 2));
        buffer.put(control);
        return buffer.array();
    }
}
