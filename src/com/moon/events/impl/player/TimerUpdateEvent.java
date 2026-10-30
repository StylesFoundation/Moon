package com.moon.events.impl.player;

import com.moon.events.Event;

public class TimerUpdateEvent extends Event {
   private int extraTicks;
   private double balance;
   private double currentBalance;

   public TimerUpdateEvent(double currentBalance) {
      this.currentBalance = currentBalance;
   }

   public int getExtraTicks() {
      return this.extraTicks;
   }

   public double getBalance() {
      return this.balance;
   }

   public double getCurrentBalance() {
      return this.currentBalance;
   }

   public TimerUpdateEvent setExtraTicks(int extraTicks) {
      this.extraTicks = extraTicks;
      return this;
   }

   public TimerUpdateEvent setBalance(double balance) {
      this.balance = balance;
      return this;
   }

   public TimerUpdateEvent setCurrentBalance(double currentBalance) {
      this.currentBalance = currentBalance;
      return this;
   }
}
