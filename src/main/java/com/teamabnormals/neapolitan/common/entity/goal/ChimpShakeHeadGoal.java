package com.teamabnormals.neapolitan.common.entity.goal;

import com.teamabnormals.neapolitan.common.entity.animal.Chimpanzee;
import com.teamabnormals.neapolitan.common.entity.util.ChimpanzeeAction;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ChimpShakeHeadGoal extends Goal {
	private final Chimpanzee chimpanzee;
	private int headShakeTimer;

	public ChimpShakeHeadGoal(Chimpanzee chimpanzeeIn) {
		this.chimpanzee = chimpanzeeIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (!this.chimpanzee.isDoingAction(ChimpanzeeAction.DEFAULT, ChimpanzeeAction.CLIMBING)) {
			return false;
		} else if (this.chimpanzee.getNavigation().isDone() && this.chimpanzee.getRandom().nextInt(120) == 0) {
			if (this.chimpanzee.isDirty() && this.chimpanzee.getGroomer() == null) {
				this.chimpanzee.level().broadcastEntityEvent(this.chimpanzee, (byte) 6);
				return true;
			} else if (this.chimpanzee.needsSunlight() && !this.chimpanzee.isInSunlight()) {
				this.chimpanzee.level().broadcastEntityEvent(this.chimpanzee, (byte) 7);
				return true;
			} else if (this.chimpanzee.needsSnack()) {
				this.chimpanzee.level().broadcastEntityEvent(this.chimpanzee, (byte) 8);
				return true;
			}
		}

		return false;
	}

	@Override
	public void start() {
		this.headShakeTimer = this.adjustedTickDelay(40);
		this.chimpanzee.getNavigation().stop();
	}

	@Override
	public boolean canContinueToUse() {
		return this.headShakeTimer > 0;
	}

	@Override
	public void stop() {
		this.headShakeTimer = 0;
	}

	@Override
	public void tick() {
		--this.headShakeTimer;
	}
}
