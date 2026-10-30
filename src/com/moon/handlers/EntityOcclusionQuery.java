package com.moon.handlers;

import java.util.UUID;

public class EntityOcclusionQuery {
   private final UUID entityUuid;
   private int queryId;
   private boolean needsQuery = true;
   private boolean culled;
   private long lastQueryTime = 0L;

   public UUID getEntityUuid() {
      return this.entityUuid;
   }

   public int getQueryId() {
      return this.queryId;
   }

   public boolean isNeedsQuery() {
      return this.needsQuery;
   }

   public boolean isCulled() {
      return this.culled;
   }

   public long getLastQueryTime() {
      return this.lastQueryTime;
   }

   public EntityOcclusionQuery setQueryId(int queryId) {
      this.queryId = queryId;
      return this;
   }

   public EntityOcclusionQuery setNeedsQuery(boolean needsQuery) {
      this.needsQuery = needsQuery;
      return this;
   }

   public EntityOcclusionQuery setCulled(boolean culled) {
      this.culled = culled;
      return this;
   }

   public EntityOcclusionQuery setLastQueryTime(long lastQueryTime) {
      this.lastQueryTime = lastQueryTime;
      return this;
   }

   public EntityOcclusionQuery(UUID uuid) {
      this.entityUuid = uuid;
   }
}
