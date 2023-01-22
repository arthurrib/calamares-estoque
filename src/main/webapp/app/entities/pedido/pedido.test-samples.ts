import dayjs from 'dayjs/esm';

import { IPedido, NewPedido } from './pedido.model';

export const sampleWithRequiredData: IPedido = {
  id: 74389,
  dataPedido: dayjs('2023-01-21'),
};

export const sampleWithPartialData: IPedido = {
  id: 47740,
  dataPedido: dayjs('2023-01-22'),
  status: 'homogeneous secured copying',
};

export const sampleWithFullData: IPedido = {
  id: 40212,
  dataPedido: dayjs('2023-01-21'),
  status: 'Outdoors sexy Towels',
  valorTotal: 29150,
};

export const sampleWithNewData: NewPedido = {
  dataPedido: dayjs('2023-01-22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
