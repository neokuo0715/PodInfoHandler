package com.example.demo.utils;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.example.demo.dto.PodsResultDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PodsResultDTOSerializer extends StdSerializer<PodsResultDTO> {
    
    public PodsResultDTOSerializer() {
        this(null);
    }
  
    public PodsResultDTOSerializer(Class<PodsResultDTO> t) {
        super(t);
    }

    @Override
    public void serialize(
    		PodsResultDTO value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
        jgen.writeStartObject(); 						//{
        jgen.writeStringField("uuid", value.getUuid());//  	uuid: value.getUuid
        jgen.writeFieldName("podsInfo");				//   pods:
        jgen.writeStartArray();							// [
        for(int i= 0;i<value.getPodsInfo().size();i++) { 
        	jgen.writeStartObject();					// {
        	jgen.writeStringField("hostName", value.getPodsInfo().get(i).getHostName());
        	jgen.writeStringField("result", value.getPodsInfo().get(i).getResult());
        	jgen.writeStringField("time", value.getPodsInfo().get(i).getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        	jgen.writeEndObject();						// }
        }
        jgen.writeEndArray();							//]
        jgen.writeEndObject();							//}
    }

	
}
