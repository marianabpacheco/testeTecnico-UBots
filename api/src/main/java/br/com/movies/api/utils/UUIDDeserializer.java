package br.com.movies.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.UUID;

public class UUIDDeserializer extends JsonDeserializer<UUID> {

        @Override
        public UUID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String uuidStr = jsonParser.getValueAsString();
            return UUID.fromString(uuidStr);
        }

}
