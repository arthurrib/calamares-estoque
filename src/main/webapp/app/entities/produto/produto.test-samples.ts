import { IProduto, NewProduto } from './produto.model';

export const sampleWithRequiredData: IProduto = {
  id: 79836,
  estoque: 97985,
  tipo: 'eco-centric',
};

export const sampleWithPartialData: IProduto = {
  id: 14475,
  estoque: 8155,
  valorIdeal: 47024,
  tipo: 'THX',
};

export const sampleWithFullData: IProduto = {
  id: 66911,
  estoque: 25058,
  estoqueMinimo: 61913,
  valorIdeal: 50210,
  tipo: 'Refined Borders',
};

export const sampleWithNewData: NewProduto = {
  estoque: 26810,
  tipo: 'Rustic Handmade',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
