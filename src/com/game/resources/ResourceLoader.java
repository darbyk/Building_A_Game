package com.game.resources;

import java.awt.image.BufferedImage;

import com.game.controllers.BufferedImageLoader;
import com.game.controllers.SpriteSheet;

public class ResourceLoader {
	private BufferedImage biWalkingWizard;
	private BufferedImage biWalkingBlueImp;
	private BufferedImage biWalkingGreenImp;
	private BufferedImage biAttackingGreenImp;
	private BufferedImage biStoneWall;
	private BufferedImage biWoodWall;
	private BufferedImage biFireball;
	private BufferedImage biGoldCoin;
	private BufferedImage biSilverCoin;
	private BufferedImage biCopperCoin;
	
	private SpriteSheet ssWalkingWizard;
	private SpriteSheet ssWalkingBlueImp;
	private SpriteSheet ssWalkingGreenImp;
	private SpriteSheet ssAttackingGreenImp;
	private SpriteSheet ssStoneWall;
	private SpriteSheet ssWoodWall;
	private SpriteSheet ssFireball;
	private SpriteSheet ssGoldCoin;
	private SpriteSheet ssSilverCoin;
	private SpriteSheet ssCopperCoin;
	
	
	public enum RESOURCE_TYPES {
		WalkingWizard,
		WalkingBlueImp,
		WalkingGreenImp,
		AttackingGreenImp,
		StoneWall,
		WoodWall,
		FireBall, 
		GoldCoin,
		SilverCoin,
		CopperCoin
	};
	
	public ResourceLoader()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		biWalkingWizard = loader.loadImage("/healer_m.png");
		biWalkingBlueImp = loader.loadImage("/imp_blue_walking_1.png");
		biWalkingGreenImp = loader.loadImage("/green_imp_walk_pitchfork.png");
		biAttackingGreenImp = loader.loadImage("/green_imp_attack_pitchfork_shield.png");
		biStoneWall = loader.loadImage("/stone_wall_texture.png");
		biWoodWall = loader.loadImage("/wooden_wall_texture.png");
		biFireball = loader.loadImage("/fireball.png");
		biGoldCoin = loader.loadImage("/gold_coins.png");
		biSilverCoin = loader.loadImage("/silver_coins.png");
		biCopperCoin = loader.loadImage("/copper_coins.png");
		
		
		ssWalkingWizard = new SpriteSheet(biWalkingWizard);
		ssWalkingBlueImp = new SpriteSheet(biWalkingBlueImp);
		ssWalkingGreenImp = new SpriteSheet(biWalkingGreenImp);
		ssAttackingGreenImp = new SpriteSheet(biAttackingGreenImp);
		ssStoneWall = new SpriteSheet(biStoneWall);
		ssWoodWall = new SpriteSheet(biWoodWall);
		ssFireball = new SpriteSheet(biFireball);
		ssGoldCoin = new SpriteSheet(biGoldCoin);
		ssSilverCoin = new SpriteSheet(biSilverCoin);
		ssCopperCoin = new SpriteSheet(biCopperCoin);
	}
	
	public BufferedImage getBufferedImageByType(RESOURCE_TYPES type)
	{
		if(RESOURCE_TYPES.WalkingWizard == type)
		{
			return biWalkingWizard;
		}
		else if(RESOURCE_TYPES.WalkingBlueImp == type)
		{
			return biWalkingBlueImp;
		}
		else if(RESOURCE_TYPES.WalkingGreenImp == type)
		{
			return biWalkingGreenImp;
		}
		else if(RESOURCE_TYPES.AttackingGreenImp == type)
		{
			return biAttackingGreenImp;
		}
		else if(RESOURCE_TYPES.StoneWall == type)
		{
			return biStoneWall;
		}
		else if(RESOURCE_TYPES.WoodWall == type)
		{
			return biWoodWall;
		}
		else if(RESOURCE_TYPES.FireBall == type)
		{
			return biFireball;
		}
		else if(RESOURCE_TYPES.GoldCoin == type)
		{
			return biGoldCoin;
		}
		else if(RESOURCE_TYPES.SilverCoin == type)
		{
			return biSilverCoin;
		}
		else if(RESOURCE_TYPES.CopperCoin == type)
		{
			return biCopperCoin;
		}
		return null;
	}
	
	public SpriteSheet getSpriteSheetByType(RESOURCE_TYPES type)
	{
		if(RESOURCE_TYPES.WalkingWizard == type)
		{
			return ssWalkingWizard;
		}
		else if(RESOURCE_TYPES.WalkingBlueImp == type)
		{
			return ssWalkingBlueImp;
		}
		else if(RESOURCE_TYPES.WalkingGreenImp == type)
		{
			return ssWalkingGreenImp;
		}
		else if(RESOURCE_TYPES.AttackingGreenImp == type)
		{
			return ssAttackingGreenImp;
		}
		else if(RESOURCE_TYPES.StoneWall == type)
		{
			return ssStoneWall;
		}
		else if(RESOURCE_TYPES.WoodWall == type)
		{
			return ssWoodWall;
		}
		else if(RESOURCE_TYPES.FireBall == type)
		{
			return ssFireball;
		}
		else if(RESOURCE_TYPES.GoldCoin == type)
		{
			return ssGoldCoin;
		}
		else if(RESOURCE_TYPES.SilverCoin == type)
		{
			return ssSilverCoin;
		}
		else if(RESOURCE_TYPES.CopperCoin == type)
		{
			return ssCopperCoin;
		}
		return null;
	}

}

