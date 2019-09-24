import { Entity } from '../entity/entity';

/** Условие приоритета */
export class ServiceLevelPriority extends Entity {
  /** Порядок следования приоритета */
  public order: number;
}
