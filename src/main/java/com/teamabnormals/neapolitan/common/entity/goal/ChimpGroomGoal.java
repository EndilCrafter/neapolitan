package com.teamabnormals.neapolitan.common.entity.goal;

import com.teamabnormals.neapolitan.common.entity.animal.Chimpanzee;
import com.teamabnormals.neapolitan.common.entity.util.ChimpanzeeAction;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;

public class ChimpGroomGoal extends Goal {
	private final Chimpanzee chimpanzee;
	private final double moveSpeed;
	private int delayCounter;
	private int groomTime;
	private int forgetTime;

	public ChimpGroomGoal(Chimpanzee chimpanzeeIn, double speed) {
		this.chimpanzee = chimpanzeeIn;
		this.moveSpeed = speed;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (this.chimpanzee.isBaby()) {
			return false;
		} else if (this.chimpanzee.getRandom().nextInt(40) == 0) {
			List<Chimpanzee> list = this.chimpanzee.level().getEntitiesOfClass(Chimpanzee.class, this.chimpanzee.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
			Chimpanzee chimpanzeeentity = null;
			double d0 = Double.MAX_VALUE;

			for (Chimpanzee chimpanzeeentity1 : list) {
				if (chimpanzeeentity1 != this.chimpanzee && chimpanzeeentity1.onGround() && chimpanzeeentity1.isDirty() && chimpanzeeentity1.getGroomer() == null) {
					double d1 = this.chimpanzee.distanceToSqr(chimpanzeeentity1);
					if (!(d1 > d0)) {
						d0 = d1;
						chimpanzeeentity = chimpanzeeentity1;
					}
				}
			}

			if (chimpanzeeentity != null) {
				this.chimpanzee.setGroomingTarget(chimpanzeeentity);
				chimpanzeeentity.setGroomer(this.chimpanzee);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canContinueToUse() {
		Chimpanzee target = this.chimpanzee.getGroomingTarget();

		if (this.forgetTime <= 0) {
			return false;
		} else if (!target.isAlive()) {
			return false;
		} else if (!target.isDirty()) {
			return false;
		} else {
			return this.chimpanzee.distanceToSqr(target) <= 256.0D;
		}
	}

	@Override
	public void start() {
		this.delayCounter = 0;
		this.groomTime = 0;
		this.forgetTime = 60;
	}

	@Override
	public void stop() {
		this.chimpanzee.setDefaultAction();
		this.chimpanzee.getGroomingTarget().setGroomer(null);
		this.chimpanzee.setGroomingTarget(null);
	}

	@Override
	public void tick() {
		Chimpanzee target = this.chimpanzee.getGroomingTarget();

		if (--this.delayCounter <= 0) {
			this.delayCounter = this.adjustedTickDelay(10);

			double d0 = this.chimpanzee.distanceToSqr(target);

			if (d0 <= 2.0D && this.chimpanzee.hasLineOfSight(target)) {
				this.chimpanzee.setAction(ChimpanzeeAction.GROOMING);

				if (++this.groomTime >= 16) {
					target.getCleaned();
				}
			} else {
				this.chimpanzee.getNavigation().moveTo(target, this.moveSpeed);
				this.chimpanzee.setDefaultAction();
				--this.forgetTime;
			}
		}

		this.chimpanzee.getLookControl().setLookAt(target, 30.0F, 30.0F);
	}
}
